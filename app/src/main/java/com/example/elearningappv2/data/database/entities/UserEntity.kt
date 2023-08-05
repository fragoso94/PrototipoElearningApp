package com.example.elearningappv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "mobile")
    var mobile: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "status")
    val status: Boolean,

    @ColumnInfo(name = "credit")
    val credit: Int = 0
)