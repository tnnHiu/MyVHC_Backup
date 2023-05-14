package com.example.myvhc.admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAdminCustomerVHCBinding

class AdminCustomerVHCFragment : Fragment() {

    private lateinit var binding: FragmentAdminCustomerVHCBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCustomerVHCBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}