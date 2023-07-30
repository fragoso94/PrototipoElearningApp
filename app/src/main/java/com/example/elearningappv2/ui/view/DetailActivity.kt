package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.databinding.ActivityDetailBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.viewmodel.CourseViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import com.example.elearningappv2.utilities.Helpers
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context, course: Course, isBuy: Boolean): Intent
        {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Helpers.COURSE_ITEM, course)
            intent.putExtra(Helpers.IS_VIEW_BUY, isBuy)
            return intent
        }
    }

    lateinit var binding: ActivityDetailBinding
    private val introductionViewModel: IntroductionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val course: Course? = intent.getParcelableExtra(Helpers.COURSE_ITEM)
        val isViewBuy = intent.getBooleanExtra(Helpers.IS_VIEW_BUY, false)
        if (course != null) {
            Picasso.get()
                .load(course.image)
                .into(binding.courseImage)
            binding.tvCourse.text = course.name
            binding.tvPrice.text = "$" + String.format("%.2f", course.price)
            binding.tvDuration.text = String.format("%.2f", course.duration) + " horas"
            binding.rbCalification.rating = course.rating
        }
        binding.buttonBuy.visibility = if (isViewBuy) View.VISIBLE else View.GONE
        binding.buttonPlay.visibility = if (!isViewBuy) View.VISIBLE else View.GONE

        binding.buttonBuy.setOnClickListener {

        }
        binding.buttonPlay.setOnClickListener {
            introductionViewModel.onListVideoSelected()
        }
        initObservers()
    }


    private fun initObservers() {
        introductionViewModel.navigateToListVideo.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToListVideo()
            }
        })
    }

    private fun goToListVideo() {
        startActivity(CourseListVideoActivity.create(this))
    }
}