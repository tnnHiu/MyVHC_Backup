package com.example.myvhc.admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAdminServiceBinding

class AdminServiceFragment : Fragment() {

    private lateinit var binding: FragmentAdminServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminServiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}