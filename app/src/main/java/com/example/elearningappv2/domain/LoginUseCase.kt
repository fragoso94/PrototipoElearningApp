package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.data.network.AuthenticationService
import com.example.elearningappv2.data.response.LoginResult
import com.example.elearningappv2.domain.model.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authenticationService.login(email, password)

}