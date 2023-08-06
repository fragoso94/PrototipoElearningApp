package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(): Boolean =
        repository.closeSessionUsersFromDatabase()
}