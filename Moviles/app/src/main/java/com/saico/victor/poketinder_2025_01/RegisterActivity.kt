package com.saico.victor.poketinder_2025_01

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.saico.victor.poketinder_2025_01.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = RegisterViewModel(this)
        observeValues()

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtPassword2.text.toString()

            registerViewModel.validateAndRegister(email, password, confirmPassword)
        }

        binding.btnBackClose.setOnClickListener {
            finish() // Regresa al LoginActivity
        }
    }

    private fun observeValues() {
        registerViewModel.emailError.observe(this) {
            it?.let { msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
        }

        registerViewModel.passwordError.observe(this) {
            it?.let { msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
        }

        registerViewModel.confirmError.observe(this) {
            it?.let { msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
        }

        registerViewModel.registerSuccess.observe(this) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
