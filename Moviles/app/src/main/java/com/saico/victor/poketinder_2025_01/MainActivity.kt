package com.saico.victor.poketinder_2025_01

import MainViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.saico.victor.poketinder_2025_01.PokemonAdapter
import com.saico.victor.poketinder_2025_01.PokemonResponse
import com.saico.victor.poketinder_2025_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var listPokemon: List<PokemonResponse> = emptyList()

    private val adapter by lazy { PokemonAdapter(listPokemon) }

    private lateinit var binding : ActivityMainBinding

    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTinderPokemon.adapter = adapter
        observeValues()
    }

    private fun observeValues() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            if (isLoading) {
                binding.tvError.isVisible = false
            }
        }

        viewModel.pokemonList.observe(this) { pokemonList ->
            adapter.list = pokemonList
            adapter.notifyDataSetChanged()
            if (pokemonList.isNotEmpty()) {
                binding.tvError.isVisible = false
            }
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            showMessage(errorMessage)
            binding.tvError.text = errorMessage
            binding.tvError.isVisible = true
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
