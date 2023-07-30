package com.example.elearningappv2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.elearningappv2.data.database.dao.ShoppingDao
import com.example.elearningappv2.data.database.dao.UserDao
import com.example.elearningappv2.data.database.entities.ShoppingEntity
import com.example.elearningappv2.data.database.entities.UserEntity
import com.example.elearningappv2.utilities.Convertes

@Database(entities = [UserEntity::class, ShoppingEntity::class], version = 1)
@TypeConverters(Convertes::class)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getShoppingDao(): ShoppingDao

}