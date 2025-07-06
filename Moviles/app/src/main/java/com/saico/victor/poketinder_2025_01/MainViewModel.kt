import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saico.victor.poketinder_2025_01.PokemonApi
import com.saico.victor.poketinder_2025_01.PokemonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log

class MainViewModel: ViewModel() {

    val pokemonList = MutableLiveData<List<PokemonResponse>>()

    val isLoading = MutableLiveData<Boolean>()

    val errorApi = MutableLiveData<String>()

    init {
        getAllPokemons()
    }

    private fun getAllPokemons() {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(PokemonApi::class.java).getPokemons()
                Log.d("POKEAPI", "Código de respuesta: ${call.code()}")
                if (call.isSuccessful) {
                    Log.d("POKEAPI", "Respuesta exitosa: ${call.body()}")
                    call.body()?.let {
                        isLoading.postValue(false)
                        if (it.results.isNullOrEmpty()) {
                            errorApi.postValue("No se encontraron Pokémon.")
                        } else {
                            pokemonList.postValue(it.results)
                        }
                    } ?: run {
                        isLoading.postValue(false)
                        errorApi.postValue("Respuesta vacía de la API.")
                    }
                } else {
                    isLoading.postValue(false)
                    errorApi.postValue("Error en la respuesta de la API: ${call.code()}")
                    Log.e("POKEAPI", "Error en la respuesta de la API: ${call.code()} ${call.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                errorApi.postValue("Error de red: ${e.message}")
                Log.e("POKEAPI", "Error de red: ${e.message}", e)
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
