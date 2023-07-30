package com.example.elearningappv2.data.network

import com.example.elearningappv2.data.model.CourseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CourseService @Inject constructor(
    private val api: CourseApiClient
) {

    suspend fun getCourses() : List<CourseModel>
    {
        return withContext(Dispatchers.IO){
            val response = api.getCourses() //retrofit.create(QuoteApiClient::class.java).getAllQuotes()
            response.body() ?: emptyList()
        }

    }
}