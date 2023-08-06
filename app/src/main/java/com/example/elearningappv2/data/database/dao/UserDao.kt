package com.example.elearningappv2.data.database.dao

import androidx.room.*
import com.example.elearningappv2.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("select * from user_table order by name")
    suspend fun getAllUser(): List<UserEntity>

    @Query("select * from user_table where name = :userName")
    suspend fun getUserId(userName: String): UserEntity

    @Query("select * from user_table where email = :userEmail")
    suspend fun getUserEmail(userEmail: String): UserEntity?

    @Query("UPDATE user_table SET status = :userStatus WHERE email = :userName")
    suspend fun updateStatusUser(userName: String, userStatus: Boolean)

    @Query("UPDATE user_table SET credit = credit + 1 WHERE email = :userName")
    suspend fun updateCreditUser(userName: String)

    @Query("select credit from user_table WHERE email = :userName")
    suspend fun getUserCredit(userName: String): Int

    @Query("UPDATE user_table SET status = 0")
    suspend fun updateCloseSessionUser(): Unit

    @Query("select * from user_table where status = 1 LIMIT 1")
    suspend fun getUserLogin(): UserEntity?

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user:UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

}