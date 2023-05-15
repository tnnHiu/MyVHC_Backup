package com.example.myvhc.admin.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.admin.activity.AdminCustomerVHCDetailActivity
import com.example.myvhc.admin.bottom_sheet.AddCustomerVehicleSheetFragment
import com.example.myvhc.databinding.FragmentAdminCustomerVHCBinding

class AdminCustomerVHCFragment : Fragment() {

    private lateinit var binding: FragmentAdminCustomerVHCBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCustomerVHCBinding.inflate(layoutInflater, container, false)

//        binding.btnAdd.setOnClickListener {
//            startActivity(Intent(context, AdminCustomerVHCDetailActivity::class.java))
//        }

        binding.btnAdd.setOnClickListener {
            AddCustomerVehicleSheetFragment().show(childFragmentManager, "newTaskTag")
        }

        return binding.root
    }
}