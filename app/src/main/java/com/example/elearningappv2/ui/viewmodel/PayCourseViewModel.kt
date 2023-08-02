package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayCourseViewModel @Inject constructor() :ViewModel()
{
    val isLoading = MutableLiveData<Boolean>()

//    isLoading.postValue(true)
//    isLoading.postValue(false)
}