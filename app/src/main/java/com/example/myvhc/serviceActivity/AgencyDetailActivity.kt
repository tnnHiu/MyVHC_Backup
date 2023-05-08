package com.example.myvhc.serviceActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.databinding.ActivityAgencyDetailBinding

class AgencyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgencyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgencyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnOrder.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }

    }
}