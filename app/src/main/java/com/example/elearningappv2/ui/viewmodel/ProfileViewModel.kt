package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.core.Event
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.getUserCreditUseCase
import com.example.elearningappv2.domain.signOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getCreditUseCase: getUserCreditUseCase,
    private val getUserUseCase: GetUserUseCase,
    val signOutUseCase: signOutUseCase
): ViewModel() {
    private val _navigateToUpdateProfile = MutableLiveData<Event<Boolean>>()
    val navigateToUpdateProfile: LiveData<Event<Boolean>>
        get() = _navigateToUpdateProfile

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    val creditModel = MutableLiveData<Int>()
    val responseModel = MutableLiveData<Boolean>()

    fun onUpdateProfileSelected() {
        _navigateToUpdateProfile.value = Event(true)
    }

    fun getUserCredit() {
        viewModelScope.launch {
            val user = getUserUseCase()
            if(user != null){
                val response = getCreditUseCase(user.email)
                creditModel.postValue(response)
            }

        }
    }

    fun signOutFirebase() {
        viewModelScope.launch {
            val res = signOutUseCase()
            responseModel.postValue(res)
        }
    }

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

}