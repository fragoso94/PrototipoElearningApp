package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivitySignUpBinding
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel

class SignUpActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SignUpActivity::class.java)
    }

    lateinit var binding: ActivitySignUpBinding
    private val introductionViewModel: IntroductionViewModel by viewModels()

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
        }
    }

    private fun initObservers() {
        introductionViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        })
    }


    private fun goToLogin() {
        startActivity(MainActivity.create(this))
    }


}