package com.example.elearningappv2.domain.model

import com.example.elearningappv2.data.database.entities.ShoppingEntity
import java.util.*

data class Shopping(
    val id: Int,
    val idUser: Int,
    val idCourse: Int,
    val date: Date,
)

fun ShoppingEntity.toDomain() = Shopping(id, idUser, idCourse, date)
fun Shopping.toDomain() = ShoppingEntity(idUser = idUser, idCourse = idCourse)