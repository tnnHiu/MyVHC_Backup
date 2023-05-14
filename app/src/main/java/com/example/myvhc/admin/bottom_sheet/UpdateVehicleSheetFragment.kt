package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentUpdateVehicleSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateVehicleSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateVehicleSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateVehicleSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}