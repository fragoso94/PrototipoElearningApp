package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.core.Event
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.UpdateEmailUseCase
import com.example.elearningappv2.domain.UpdateUserUseCase
import com.example.elearningappv2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {

    val userModel = MutableLiveData<User?>()
    val updateEmailModel = MutableLiveData<Boolean>()
    val updateUserModel = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    fun getDataUserLogin(){
        viewModelScope.launch {
            val user = getUserUseCase()
            userModel.postValue(user)
        }
    }

    fun updateUserDataBase(user: User){
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = updateUserUseCase(user)
            if(response){
                val res = updateEmailUseCase(user.email)
                updateEmailModel.postValue(res)
                updateUserModel.postValue(response)
            }
            isLoading.postValue(false)
        }
    }
}