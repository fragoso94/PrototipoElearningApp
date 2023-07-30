package com.example.elearningappv2.ui.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.elearningappv2.databinding.CardviewCourseBinding
import com.example.elearningappv2.domain.model.Course
import com.squareup.picasso.Picasso

class ViewHolder(
    private val cardviewCourseBinding: CardviewCourseBinding,
    private val onClickListener:(Course) -> Unit
) : RecyclerView.ViewHolder(cardviewCourseBinding.root)
{
    fun bindCourse(course: Course){
        Picasso.get()
            .load(course.image)
            .into(cardviewCourseBinding.courseImage)
        cardviewCourseBinding.tvCourse.text = course.name
        cardviewCourseBinding.tvPrice.text = "$" + String.format("%.2f", course.price)
        cardviewCourseBinding.tvHours.text = " "
        cardviewCourseBinding.rbCalification.rating = course.rating

        cardviewCourseBinding.cardviewLayout.setOnClickListener{
            onClickListener(course)
        }
    }
}