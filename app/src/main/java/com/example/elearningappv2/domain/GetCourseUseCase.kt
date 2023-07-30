package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.Course
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(
    private val repository: CourseRepository
)
{
    suspend operator fun invoke(): List<Course>?
    {
        val courses = repository.getAllCourseFromApi()
        return if(courses.isNotEmpty()) courses else emptyList()
    }
}