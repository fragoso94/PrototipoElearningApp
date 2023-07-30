package com.example.elearningappv2.data.network

import com.example.elearningappv2.data.model.CourseModel
import retrofit2.Response
import retrofit2.http.GET

interface CourseApiClient {
    @GET("Curso")
    suspend fun getCourses() : Response<List<CourseModel>>
}