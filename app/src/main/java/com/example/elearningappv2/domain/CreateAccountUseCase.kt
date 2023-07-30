package com.example.elearningappv2.domain

import com.example.elearningappv2.data.network.AuthenticationService
import com.example.elearningappv2.domain.model.SimpleResponse
import com.example.elearningappv2.domain.model.User
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(userSignIn: User): SimpleResponse {
        val accountCreated =
            authenticationService.createAccount(userSignIn.email, userSignIn.password) != null
        val response = SimpleResponse(
            exito = accountCreated,
            mensaje = if(accountCreated) "La cuenta se creo correstamente" else "No se pudo crear la cuenta"
        )
        return response
    }
}