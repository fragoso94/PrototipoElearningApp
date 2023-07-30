package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.elearningappv2.databinding.ActivityMainBinding
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.ui.viewmodel.CourseViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import com.example.elearningappv2.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    //viewmodels
    private val introductionViewModel: IntroductionViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                if(etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()){
                    loginViewModel.loginSelected(etEmail.text.toString(), etPassword.text.toString())
                    loginViewModel.responseModel.observe(this@MainActivity, Observer {
                        if (it.exito){
                            introductionViewModel.onHomeSelected()
                        }
                        else{
                            Log.d("dfragoso94",it.mensaje)
                        }
                    })
                }
            }
            tvSignup.setOnClickListener { introductionViewModel.onSignInSelected() }
        }
    }

    private fun initObservers() {
        introductionViewModel.navigateToHome.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToHome()
            }
        })
        introductionViewModel.navigateToSignIn.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToSingIn()
            }
        })
        loginViewModel.isLoading.observe(this, Observer{
            binding.progress.isVisible = it
        })
    }

    private fun goToSingIn() {
        startActivity(SignUpActivity.create(this))
    }

    private fun goToHome() {
        startActivity(HomeActivity.create(this))
    }

}