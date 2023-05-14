package com.example.myvhc.admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAdminCustomerBinding

class AdminCustomerFragment : Fragment() {

    private lateinit var binding: FragmentAdminCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCustomerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}