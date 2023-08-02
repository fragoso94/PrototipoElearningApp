package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.data.response.LoginResult
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.LoginUseCase
import com.example.elearningappv2.domain.UpdateUserStatusUseCase
import com.example.elearningappv2.domain.model.SimpleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    val updateUserStatusUseCase: UpdateUserStatusUseCase

) : ViewModel()
{
    val responseModel = MutableLiveData<SimpleResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun loginSelected(user: String, password: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val result = loginUseCase(user.trim(), password.trim())) {
                LoginResult.Error -> {
                    responseModel.postValue(SimpleResponse(exito = false, mensaje = "Error al autenticarse."))
                }
                is LoginResult.Success -> {
                    //update status user
                    /*val userExist = getUserUseCase(user)
                    if (userExist != null){
                        val response = updateUserStatusUseCase(user, true)
                        if (response){
                            responseModel.postValue(SimpleResponse(exito = true, mensaje = "La cuenta se verifico correctamente."))
                        }
                    }*/
                    responseModel.postValue(SimpleResponse(exito = true, mensaje = "La cuenta se verifico correctamente."))
                }
            }
            isLoading.postValue(false)
        }
    }
}