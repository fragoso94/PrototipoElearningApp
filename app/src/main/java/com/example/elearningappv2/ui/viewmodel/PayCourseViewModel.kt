package com.example.elearningappv2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elearningappv2.core.Event
import com.example.elearningappv2.domain.GetUserUseCase
import com.example.elearningappv2.domain.InsertShoppingUseCase
import com.example.elearningappv2.domain.model.Shopping
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PayCourseViewModel @Inject constructor(
    val shopingUseCase : InsertShoppingUseCase,
    val userUserCase: GetUserUseCase
) :ViewModel()
{
    val responseModel = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    fun shopCourse(idCourse: Int){
        viewModelScope.launch {
            isLoading.postValue(true)
            val user = userUserCase()
            if(user != null){
                val shop = Shopping(id= 0, idUser = user.id, idCourse = idCourse, date = Date())
                val result = shopingUseCase(shop)
                responseModel.postValue(result)
            }
            isLoading.postValue(false)
        }
    }

    fun onHomeSelected() {
        _navigateToHome.value = Event(true)
    }

}