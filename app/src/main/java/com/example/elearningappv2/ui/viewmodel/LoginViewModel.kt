package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.data.response.LoginResult
import com.example.elearningappv2.domain.LoginUseCase
import com.example.elearningappv2.domain.model.SimpleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel()
{
    val responseModel = MutableLiveData<SimpleResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun loginSelected(user: String, password: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val result = loginUseCase(user, password)) {
                LoginResult.Error -> {
                    responseModel.postValue(SimpleResponse(exito = false, mensaje = "Error al autenticarse."))
                }
                is LoginResult.Success -> {
                    responseModel.postValue(SimpleResponse(exito = true, mensaje = "La cuenta se verifico correctamente."))
                }
            }
            isLoading.postValue(false)
        }
    }
}