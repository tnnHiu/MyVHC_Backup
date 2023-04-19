package com.example.myvhc.fragment_main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.AgencyMapsActivity
import com.example.myvhc.databinding.FragmentAgencyBinding


class FragmentAgency : Fragment() {

    private lateinit var binding: FragmentAgencyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentAgencyBinding.inflate(inflater, container, false)

        binding.motorbike.setOnClickListener {
            startActivity(Intent(context, AgencyMapsActivity::class.java))
        }
        binding.car.setOnClickListener {
            startActivity(Intent(context, AgencyMapsActivity::class.java))
        }

        return binding.root
    }
}