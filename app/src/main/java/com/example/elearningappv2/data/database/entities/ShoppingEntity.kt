package com.example.elearningappv2.data.database.entities

import androidx.room.*
import com.example.elearningappv2.utilities.Convertes
import java.util.*

@Entity(tableName = "shopping_table")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "idUser")
    val idUser: Int,

    @ColumnInfo(name = "idCourse")
    val idCourse: Int,

    @ColumnInfo(name = "date")
    @TypeConverters(Convertes::class)
    val date: Date = Date()
)