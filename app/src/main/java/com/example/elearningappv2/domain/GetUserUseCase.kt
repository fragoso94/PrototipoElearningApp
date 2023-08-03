package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(user: String? = null): User? =
        repository.getUserFromDatabase(user)

}
