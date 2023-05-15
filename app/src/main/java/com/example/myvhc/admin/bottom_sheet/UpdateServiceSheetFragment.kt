package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentUpdateServiceSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateServiceSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateServiceSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateServiceSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}