package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.databinding.ActivityMainBinding
import com.example.elearningappv2.ui.viewmodel.CourseViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    //viewmodels
    //private val courseViewModel: CourseViewModel by viewModels()
    private val introductionViewModel: IntroductionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*courseViewModel.onCreateCourse()
        courseViewModel.courseModel.observe(this, Observer
        {
            //binding.tvPrueba.text = it.name + "\n"
            it?.forEach { item ->
                Log.d("dfragoso", item.toString())
            }
        })*/
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btnLogin.setOnClickListener { introductionViewModel.onHomeSelected() }
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
    }

    private fun goToSingIn() {
        startActivity(SignUpActivity.create(this))
    }

    private fun goToHome() {
        startActivity(HomeActivity.create(this))
    }

}