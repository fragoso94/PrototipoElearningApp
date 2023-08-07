package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.elearningappv2.databinding.ActivityUpdateBinding
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.ui.view.fragments.ProfileFragment
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
    private var currentUser: User = User(0, "", "", "", "", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        listeners()
    }

    private fun listeners(){
        with(binding){
            buttonSave.setOnClickListener {
                val user = User(
                    id = currentUser.id,
                    name = etName.getText().toString(),
                    email = etEmailAddress.getText().toString(),
                    mobileNumber = etMobile.getText().toString(),
                    password = currentUser.password,
                    status = currentUser.status
                )
//                Log.d("CURRENT_USER",binding.etName.getText().toString())
//                Log.d("CURRENT_USER",binding.etEmailAddress.getText().toString())
//                Log.d("CURRENT_USER",binding.etMobile.getText().toString())
                updateViewModel.updateUserDataBase(user)
                updateViewModel.updateUserModel.observe(this@UpdateActivity, Observer {
                    if (it){
                        updateViewModel.onLoginSelected()
                        Toast.makeText(this@UpdateActivity, "Datos actualizados correctamente.!!!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@UpdateActivity, "No se pudo actualizar los datos :(", Toast.LENGTH_SHORT).show()
                    }
                })
            }
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
                currentUser = user
            }
        })
        updateViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToProfile()
            }
        })
        updateViewModel.isLoading.observe(this, Observer{
            binding.progress.isVisible = it
        })
    }

    private fun goToProfile() {
        startActivity(HomeActivity.create(this))
        //finish()
    }
}