package com.example.elearningappv2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elearningappv2.core.Event
import com.example.elearningappv2.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor() : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>>
        get() = _navigateToSignIn

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    private val _navigateToListVideo = MutableLiveData<Event<Boolean>>()
    val navigateToListVideo: LiveData<Event<Boolean>>
        get() = _navigateToListVideo

    private val _navigateToDetailCourse = MutableLiveData<Event<Course>>()
    val navigateToDetailCourse: LiveData<Event<Course>>
        get() = _navigateToDetailCourse

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }

    fun onHomeSelected(){
        _navigateToHome.value = Event(true)
    }

    fun onListVideoSelected(){
        _navigateToListVideo.value = Event(true)
    }

    fun onDetailCourseSelected(course: Course){
        _navigateToDetailCourse.value = Event(course)
    }
}