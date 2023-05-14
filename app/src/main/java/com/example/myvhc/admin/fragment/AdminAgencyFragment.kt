package com.example.myvhc.admin.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.admin.activity.AdminAgencyDetailActivity
import com.example.myvhc.databinding.FragmentAdminAgencyBinding

class AdminAgencyFragment : Fragment() {

    private lateinit var binding: FragmentAdminAgencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAgencyBinding.inflate(layoutInflater, container, false)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(context, AdminAgencyDetailActivity::class.java))
        }

//        binding.btnAdd.setOnClickListener {
//            AddAgencySheetFragment().show(childFragmentManager, "newTaskTag")
//        }

        return binding.root
    }
}