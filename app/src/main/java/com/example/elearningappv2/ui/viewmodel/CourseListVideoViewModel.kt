package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.UpdateCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseListVideoViewModel @Inject constructor(
    val updateCreditUseCase: UpdateCreditUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel()
{
    val responseModel = MutableLiveData<Boolean>()

    fun insertUserCredit() {
        viewModelScope.launch {
            val user = getUserUseCase()
            if(user != null){
                val response = updateCreditUseCase(user.email)
                responseModel.postValue(response)
            }
        }
    }
}