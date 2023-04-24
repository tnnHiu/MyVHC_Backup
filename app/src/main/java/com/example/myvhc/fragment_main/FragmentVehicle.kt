package com.example.myvhc.fragment_main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.databinding.FragmentVehicleBinding
import com.example.myvhc.myVHCActivity.AddMyVehicleActivity


class FragmentVehicle : Fragment() {

    private lateinit var binding: FragmentVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVehicleBinding.inflate(layoutInflater, container, false)

        binding.btnAddVehicle.setOnClickListener {
            startActivity(Intent(context, AddMyVehicleActivity::class.java))
        }

        return binding.root
    }

}