package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.databinding.ActivityUpdateBinding
import com.example.elearningappv2.ui.viewmodel.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, UpdateActivity::class.java)
    }

    lateinit var binding: ActivityUpdateBinding
    val updateViewModel: UpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
        observers()
    }

    private fun listeners(){
        binding.buttonSave.setOnClickListener {
            updateViewModel.onLoginSelected()
        }
    }

    private fun observers(){
        updateViewModel.getDataUserLogin()
        updateViewModel.userModel.observe(this, Observer { user ->
            if (user != null){
                with(binding){
                    etName.setText(user.name)
                    etEmailAddress.setText(user.email)
                    etMobile.setText(user.mobileNumber)
                }
            }
        })
        updateViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        })
    }

    private fun goToLogin() {
        startActivity(MainActivity.create(this))
    }
}