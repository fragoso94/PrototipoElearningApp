package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import javax.inject.Inject

class getUserCreditUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(user: String): Int =
        repository.getUserCreditFromDatabase(user)
}
