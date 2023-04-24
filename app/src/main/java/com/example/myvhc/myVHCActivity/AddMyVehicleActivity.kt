package com.example.myvhc.myVHCActivity

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

    }
}