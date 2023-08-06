package com.example.elearningappv2.domain

import com.example.elearningappv2.data.network.AuthenticationService
import com.example.elearningappv2.domain.model.SimpleResponse
import com.example.elearningappv2.domain.model.User
import javax.inject.Inject

class signOutUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(): Boolean = authenticationService.singOutAccount()
}