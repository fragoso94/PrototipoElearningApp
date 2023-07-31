package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.domain.model.toDomain
import javax.inject.Inject

class UpdateUserStatusUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(user: String, status: Boolean): Boolean =
        repository.updateUserStatusFromDatabase(user, status)

}