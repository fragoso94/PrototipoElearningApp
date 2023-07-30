package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.User
import javax.inject.Inject

class GetCourseShoppingUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(userId: Int): List<Int> =
        repository.getAllShoppingFromDatabase(userId)

}
