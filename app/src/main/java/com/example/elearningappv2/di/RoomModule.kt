package com.example.elearningappv2.di

import android.content.Context
import androidx.room.Room
import com.example.elearningappv2.data.database.CourseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val QUOTE_DATABASE_NAME = "db_elearning"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CourseDatabase::class.java, QUOTE_DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideUserDao(db: CourseDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideShoppingDao(db: CourseDatabase) = db.getShoppingDao()
}