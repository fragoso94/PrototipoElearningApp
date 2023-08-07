package com.example.elearningappv2.domain

import com.example.elearningappv2.data.network.AuthenticationService
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(user: String): Boolean = authenticationService.updateEmailAccount(user)
}