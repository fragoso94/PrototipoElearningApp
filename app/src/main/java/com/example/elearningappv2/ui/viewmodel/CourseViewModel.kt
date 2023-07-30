package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.domain.GetCourseUseCase
import com.example.elearningappv2.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase
) : ViewModel()
{
    val courseModel = MutableLiveData<List<Course>?>()
    //val isLoading = MutableLiveData<Boolean>()

    //función init cuando se renderiza por primera vez la pantalla
    fun onCreateCourse() {
        viewModelScope.launch {
            //isLoading.postValue(true)
            val result = getCourseUseCase()
            if (!result.isNullOrEmpty())
            {
                courseModel.postValue(result)
                //courseModel.postValue(result[0])
                //isLoading.postValue(false)
            }
        }
    }
}