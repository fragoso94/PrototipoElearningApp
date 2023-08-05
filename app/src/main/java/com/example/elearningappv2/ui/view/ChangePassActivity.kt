package com.example.elearningappv2.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.elearningappv2.databinding.ActivityChangePassBinding

class ChangePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener{
           // Toast.makeText(applicationContext, "Editar imagen", Toast.LENGTH_SHORT).show()
        }
    }
}