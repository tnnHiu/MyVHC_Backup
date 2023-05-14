package com.example.myvhc.admin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.admin.bottom_sheet.UpdateVehicleSheetFragment
import com.example.myvhc.databinding.ActivityAdminVehicleDetailBinding

class AdminVehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            UpdateVehicleSheetFragment().show(supportFragmentManager, "newTaskTag")
        }

    }
}