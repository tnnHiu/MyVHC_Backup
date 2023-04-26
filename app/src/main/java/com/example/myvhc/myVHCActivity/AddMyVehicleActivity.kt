package com.example.myvhc.myVHCActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.databinding.ActivityAddMyVehicleBinding

class AddMyVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyVehicleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.motor.setOnClickListener {
            startActivity(Intent(this, AddMotorActivity::class.java))
        }

    }
}