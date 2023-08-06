package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.domain.GetCourseShoppingUseCase
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCourseViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getCourseShopping: GetCourseShoppingUseCase
) : ViewModel() {
    val userModel = MutableLiveData<User?>()
    val courseShoppingModel = MutableLiveData<List<Int>>()

    fun getListShoppingCourse(){
        viewModelScope.launch {
            val userResponse = getUserUseCase()
            if(userResponse != null){
                userModel.postValue(userResponse)
                val result = getCourseShopping(userResponse.id)
                if(!result.isNullOrEmpty()){
                    courseShoppingModel.postValue(result)
                }
            }
        }
    }
}