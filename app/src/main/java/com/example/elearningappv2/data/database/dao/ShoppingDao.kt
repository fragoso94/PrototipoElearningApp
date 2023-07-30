package com.example.elearningappv2.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.elearningappv2.data.database.entities.ShoppingEntity

@Dao
interface ShoppingDao {
    @Query("select idCourse from shopping_table where idUser = :userId")
    suspend fun getAllShopping(userId: Int): List<Int>

    @Insert
    suspend fun insertShopping(shopping: ShoppingEntity)
}