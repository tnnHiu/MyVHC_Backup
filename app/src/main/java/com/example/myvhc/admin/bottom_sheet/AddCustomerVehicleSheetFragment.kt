package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAddCustomerVehicleSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCustomerVehicleSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddCustomerVehicleSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerVehicleSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}