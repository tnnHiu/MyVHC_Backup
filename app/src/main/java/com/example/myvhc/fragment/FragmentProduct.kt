package com.example.myvhc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.databinding.FragmentProductBinding

class FragmentProduct : Fragment() {
    private lateinit var binding: FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}