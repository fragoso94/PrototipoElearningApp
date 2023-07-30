package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityCourseListVideoBinding

class CourseListVideoActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, CourseListVideoActivity::class.java)
    }

    lateinit var binding : ActivityCourseListVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseListVideoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pathVideo = "android.resource://" + applicationContext.packageName + "/" // + R.raw.Curso1
        initVideoUI(pathVideo, view)
    }

    private fun initVideoUI(path: String, view: View){
        with(binding){
            val mediaController1 = MediaController(view.context)
            val mediaController2 = MediaController(view.context)
            val mediaController3 = MediaController(view.context)

            mediaController1.setAnchorView(videoView1) // R.raw.Curso1
            mediaController2.setAnchorView(videoView2) // R.raw.Curso1
            mediaController3.setAnchorView(videoView3) // R.raw.Curso1

            videoView1.setVideoURI(Uri.parse(path + R.raw.curso1))
            videoView1.setMediaController(mediaController1)
            videoView1.requestFocus()
            fbPlay1.setOnClickListener {
                videoView1.start()
            }

            videoView2.setVideoURI(Uri.parse(path + R.raw.curso2))
            videoView2.setMediaController(mediaController2)
            videoView2.requestFocus()
            fbPlay2.setOnClickListener {
                videoView2.start()
            }

            videoView3.setVideoURI(Uri.parse(path + R.raw.curso3))
            videoView3.setMediaController(mediaController3)
            videoView3.requestFocus()
            fbPlay3.setOnClickListener {
                videoView3.start()
            }
        }

    }
}