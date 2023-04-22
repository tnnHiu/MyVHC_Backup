package com.example.myvhc.myVHCDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.databinding.ActivityMyMotorDetailBinding

class MyMotorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyMotorDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMotorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}