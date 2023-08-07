package com.example.elearningappv2.data.network

import com.example.elearningappv2.data.database.dao.UserDao
import com.example.elearningappv2.data.response.LoginResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(
    private val firebase: FirebaseClient
) {

    suspend fun login(email: String, password: String): LoginResult = runCatching {
        firebase.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    suspend fun createAccount(email: String, password: String): AuthResult? {
        return firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun singOutAccount(): Boolean {
        return try {
            firebase.auth.signOut()
            true
        }
        catch (e: java.lang.Exception){
            false
        }
    }

    suspend fun updateEmailAccount(user: String): Boolean {
        var response = false
        val currentUser = firebase.auth.currentUser
        currentUser!!.updateEmail(user)
            .addOnCompleteListener { task ->
                response = task.isSuccessful
            }
        return response
    }

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResult.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResult.Success(result.user?.isEmailVerified ?: false)
        }
    }
}