package com.example.myvhc.fragment_main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentVehicleBinding
import com.example.myvhc.myVHCDetailActivity.MyMotorDetailActivity


class FragmentVehicle : Fragment() {

    private lateinit var binding: FragmentVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVehicleBinding.inflate(layoutInflater, container, false)

        binding.motor.setOnClickListener {
            startActivity(Intent(context, MyMotorDetailActivity::class.java))
        }

        return binding.root
    }

}