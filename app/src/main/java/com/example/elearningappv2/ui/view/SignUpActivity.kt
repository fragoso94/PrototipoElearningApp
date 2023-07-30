package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.elearningappv2.core.Ex.dismissKeyboard
import com.example.elearningappv2.databinding.ActivitySignUpBinding
import com.example.elearningappv2.domain.model.SimpleResponse
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.ui.viewmodel.CreateAccountViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SignUpActivity::class.java)
    }

    lateinit var binding: ActivitySignUpBinding
    private val introductionViewModel: IntroductionViewModel by viewModels()
    private val createAccountViewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            tvLogin.setOnClickListener { introductionViewModel.onLoginSelected() }
            btnSignup.setOnClickListener {
                it.dismissKeyboard()
                val response = validationForm()
                if (response.exito){
                    val userSignIn = User(
                        id = 0,
                        name = etName.text.toString(),
                        email = etEmailAddress.text.toString(),
                        mobileNumber = etMobile.text.toString(),
                        password = etCreatePassword.text.toString()
                    )
                    createAccountViewModel.signInUser(userSignIn)
                    createAccountViewModel.responseModel.observe(this@SignUpActivity, Observer
                    {
                        if (it.exito) {
                            introductionViewModel.onLoginSelected()
                        }
                        Log.d("dfragoso94",it.mensaje)
                    })
                }
                else{
                    Log.d("dfragoso94","Verifique sus datos por favor!")
                }
            }
        }
    }

    private fun initObservers() {
        introductionViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        })
        createAccountViewModel.isLoading.observe(this, Observer{
            binding.progress.isVisible = it
        })
    }

    private fun goToLogin() {
        startActivity(MainActivity.create(this))
    }

    private fun validationForm(): SimpleResponse{
        val response = SimpleResponse()

        with(binding){
            if (etName.text.isEmpty()){
                response.exito = false
                response.mensaje = "El nombre es requerido."
            }
            else if (etEmailAddress.text.isEmpty()){
                response.exito = false
                response.mensaje = "El correo es requerido."
            }
            else if (etMobile.text.isEmpty()){
                response.exito = false
                response.mensaje = "El número de telefono es requerido."
            }
            else if (etCreatePassword.text.isEmpty()){
                response.exito = false
                response.mensaje = "La contraseña es requerido."
            }
            else if (etConfirmPassword.text.isEmpty()){
                response.exito = false
                response.mensaje = "La confirmación de contraseña es requerido."
            }
            else{
                response.exito = true
                response.mensaje = ""
            }
        }

        return response
    }
}