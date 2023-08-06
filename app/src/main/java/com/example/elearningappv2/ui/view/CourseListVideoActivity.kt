package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityCourseListVideoBinding
import com.example.elearningappv2.ui.viewmodel.CourseListVideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseListVideoActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, CourseListVideoActivity::class.java)
    }

    lateinit var binding : ActivityCourseListVideoBinding
    private val profileViewModel: CourseListVideoViewModel by viewModels()

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
            videoView1.setOnInfoListener { mediaPlayer, what, extra ->
                // solo se insertará la primera vez y cuando finalize de ver los videos.
                if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    profileViewModel.insertUserCredit()
                    profileViewModel.responseModel.observe(this@CourseListVideoActivity, Observer {
                        Log.d("dfragoso94", "El credito se agrego correctament, estatus: $it")
                    })
                }
                true
            }


            videoView2.setVideoURI(Uri.parse(path + R.raw.curso2))
            videoView2.setMediaController(mediaController2)
            videoView2.requestFocus()
            fbPlay2.setOnClickListener {
                videoView2.start()
            }
            videoView2.setOnInfoListener { mediaPlayer, what, extra ->
                // solo se insertará la primera vez y cuando finalize de ver los videos.
                if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    profileViewModel.insertUserCredit()
                    profileViewModel.responseModel.observe(this@CourseListVideoActivity, Observer {
                        Log.d("dfragoso94", "El credito se agrego correctament, estatus: $it")
                    })
                }
                true
            }

            videoView3.setVideoURI(Uri.parse(path + R.raw.curso3))
            videoView3.setMediaController(mediaController3)
            videoView3.requestFocus()
            fbPlay3.setOnClickListener {
                videoView3.start()
            }
            videoView3.setOnInfoListener { mediaPlayer, what, extra ->
                // solo se insertará la primera vez y cuando finalize de ver los videos.
                if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    profileViewModel.insertUserCredit()
                    profileViewModel.responseModel.observe(this@CourseListVideoActivity, Observer {
                        Log.d("dfragoso94", "El credito se agrego correctamente, estatus: $it")
                    })
                }
                true
            }
        }

    }
}