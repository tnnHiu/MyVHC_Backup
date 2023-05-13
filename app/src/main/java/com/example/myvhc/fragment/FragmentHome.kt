package com.example.myvhc.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.admin.DashboardAdminActivity
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.serviceActivity.AgencyDetailActivity
import com.google.firebase.auth.FirebaseAuth

class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.test.setOnClickListener{
            startActivity(Intent(context, DashboardAdminActivity::class.java))
        }

        return binding.root
    }
}