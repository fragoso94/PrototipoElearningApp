package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.domain.model.toDomain
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(user: User): Boolean =
        repository.insertUserFromDatabase(user.toDomain())

}