package com.example.elearningappv2.data

import com.example.elearningappv2.data.network.CourseService
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.domain.model.toDomain
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val api: CourseService
    //private val quoteDao: QuoteDao
    )
{
    suspend fun getAllCourseFromApi(): List<Course>{
        val response = api.getCourses()
        //Mapper
        val responseMap = response.map {
            it.toDomain()
        }
        return responseMap
    }
}