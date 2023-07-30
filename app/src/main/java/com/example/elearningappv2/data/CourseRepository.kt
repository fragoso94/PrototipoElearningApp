package com.example.elearningappv2.data

import com.example.elearningappv2.data.database.dao.ShoppingDao
import com.example.elearningappv2.data.database.dao.UserDao
import com.example.elearningappv2.data.database.entities.ShoppingEntity
import com.example.elearningappv2.data.database.entities.UserEntity
import com.example.elearningappv2.data.network.CourseService
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.domain.model.toDomain
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val api: CourseService,
    private val userDao: UserDao,
    private val shoppingDao: ShoppingDao
) {
    suspend fun getAllCourseFromApi(): List<Course> {
        val response = api.getCourses()
        //Mapper
        val responseMap = response.map {
            it.toDomain()
        }
        return responseMap
    }

    suspend fun insertUserFromDatabase(user: UserEntity): Boolean {
        return try {
            userDao.insertAll(user)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    suspend fun updateUserFromDatabase(user: UserEntity): Boolean {
        return try {
            userDao.update(user)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    suspend fun getUserFromDatabase(email: String): User {
        return userDao.getUserEmail(email).toDomain()
    }

    suspend fun insertShoppingFromDatabase(shoppin: ShoppingEntity): Boolean {
        return try {
            shoppingDao.insertShopping(shoppin)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    suspend fun getAllShoppingFromDatabase(userId: Int): List<Int> {
        return shoppingDao.getAllShopping(userId)
    }

}