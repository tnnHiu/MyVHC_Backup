package com.example.myvhc.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.admin.bottom_sheet.AddVehicleSheetFragment
import com.example.myvhc.databinding.FragmentAdminVehicleBinding

class AdminVehicleFragment : Fragment() {

    private lateinit var binding: FragmentAdminVehicleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminVehicleBinding.inflate(layoutInflater, container, false)

        binding.btnAdd.setOnClickListener {
            AddVehicleSheetFragment().show(childFragmentManager, "newTaskTag")
        }

        return binding.root
    }

}