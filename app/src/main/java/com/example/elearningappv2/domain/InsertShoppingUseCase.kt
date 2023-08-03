package com.example.elearningappv2.domain

import com.example.elearningappv2.data.CourseRepository
import com.example.elearningappv2.domain.model.Shopping
import com.example.elearningappv2.domain.model.User
import com.example.elearningappv2.domain.model.toDomain
import javax.inject.Inject

class InsertShoppingUseCase @Inject constructor(
    private val repository: CourseRepository
) {

    suspend operator fun invoke(shop: Shopping): Boolean =
        repository.insertShoppingFromDatabase(shop.toDomain())

}