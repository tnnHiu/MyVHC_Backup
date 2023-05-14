package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAddVehicleSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddVehicleSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddVehicleSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentdfdfdfd
        binding = FragmentAddVehicleSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}