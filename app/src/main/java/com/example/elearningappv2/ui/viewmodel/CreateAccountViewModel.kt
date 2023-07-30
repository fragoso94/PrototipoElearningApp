package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.domain.CreateAccountUseCase
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.InsertUserUseCase
import com.example.elearningappv2.domain.model.SimpleResponse
import com.example.elearningappv2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    val createAccountUseCase: CreateAccountUseCase,
    val userUseCase: InsertUserUseCase
    ) : ViewModel()
{
    val responseModel = MutableLiveData<SimpleResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun signInUser(userSignIn: User) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val accountCreated = createAccountUseCase(userSignIn)
            //guardamos el usuario en la db local
            if(accountCreated.exito){
                val resulInsert = userUseCase(userSignIn)
                if (resulInsert){
                    responseModel.postValue(accountCreated)
                }
            }
            isLoading.postValue(false)
        }
    }
}