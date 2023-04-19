package com.example.myvhc.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myvhc.adapter.AdapterHomeAgency
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.models.Agency

import com.example.myvhc.models.OrderAgency


class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val items: MutableList<Item> = ArrayList()
//        val agency1 = Agency("Tiến Thu", "200")
//        items.add(Item(0, agency1))
//        val contact1 = ContactAgency("33/61 Hương Lộ Ngọc Hiệp", "6:00 - 17:00", "0967070842")
//        items.add(Item(1, contact1))
//        val order1 = OrderAgency("Đặt lịch hẹn")
//        items.add(Item(2, order1))
//
//        binding.rvAgencyHome.adapter = AdapterHomeAgency(items)

        return binding.root
    }
}