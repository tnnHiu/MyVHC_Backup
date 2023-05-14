package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentUpdateAgencySheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateAgencySheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateAgencySheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateAgencySheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}