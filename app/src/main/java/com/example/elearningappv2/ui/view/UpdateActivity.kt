package com.example.elearningappv2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.elearningappv2.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}