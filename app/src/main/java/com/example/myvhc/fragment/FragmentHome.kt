package com.example.myvhc.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.serviceActivity.AgencyDetailActivity

class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.test.setOnClickListener{
            startActivity(Intent(context, AgencyDetailActivity::class.java))
        }

        return binding.root
    }
}