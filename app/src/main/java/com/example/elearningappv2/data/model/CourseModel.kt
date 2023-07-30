package com.example.elearningappv2.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CourseModel(
    @SerializedName("id") var id: Int,
    @SerializedName("image") var image: String, //var image: Int,
    @SerializedName("category") var category : String,
    @SerializedName("name") var name:String,
    @SerializedName("duration") var duration: Float,
    @SerializedName("price") var price: Float,
    @SerializedName("rating") var rating: Float
)