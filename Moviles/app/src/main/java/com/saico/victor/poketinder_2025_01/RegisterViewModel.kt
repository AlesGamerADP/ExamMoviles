package com.saico.victor.poketinder_2025_01

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel(
    val context : Context
) : ViewModel() {
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val confirmError = MutableLiveData<String>()
    val registerSuccess = MutableLiveData<Boolean>()

    private var sharedPreferencesRepository: SharedPreferencesRepository =
        SharedPreferencesRepository().also {
            it.setSharedPreference(context)
        }

    fun validateAndRegister(email: String, password: String, confirmPassword: String) {
        emailError.value = null
        passwordError.value = null
        confirmError.value = null

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError.value = "Correo inválido"
            return
        }

        if (password.length < 8) {
            passwordError.value = "Mínimo 8 caracteres"
            return
        }

        if (password != confirmPassword) {
            confirmError.value = "Las contraseñas no coinciden"
            return
        }

        sharedPreferencesRepository.saveUserEmail(email)
        sharedPreferencesRepository.saveUserPassword(password)
        registerSuccess.value = true
    }
}