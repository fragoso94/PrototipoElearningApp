package com.example.elearningappv2.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningappv2.databinding.CardviewCourseBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.view.viewholder.ViewHolder


class RecyclerAdapter(
    private var courses: List<Course>,
    private val onClickListener : (Course) -> Unit
): RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = CardviewCourseBinding.inflate(view, parent, false)
        return ViewHolder(binding, onClickListener) //clickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCourse(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}