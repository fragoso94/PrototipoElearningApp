package com.example.elearningappv2.domain.model

import com.example.elearningappv2.data.database.entities.UserEntity

data class User(
    val id: Int,
    val name: String,
    val email: String,
    var mobileNumber: String,
    var password: String,
    var status: Boolean,
    var cursos: MutableList<Course> = mutableListOf()
)

fun UserEntity.toDomain() = User(id, name, email, mobile, password, status)
fun User.toDomain() = UserEntity(name= name, email= email, mobile = mobileNumber, password = password, status= status)