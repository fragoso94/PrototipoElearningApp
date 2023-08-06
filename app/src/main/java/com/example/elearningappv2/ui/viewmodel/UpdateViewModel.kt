package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.core.Event
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val userModel = MutableLiveData<User?>()

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

}