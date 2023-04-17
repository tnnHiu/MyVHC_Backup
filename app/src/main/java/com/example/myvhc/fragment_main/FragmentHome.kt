package com.example.testui.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myvhc.R
import com.example.myvhc.adapter.AdapterHomeAgency
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.testui.model.Agency
import com.example.testui.model.ContactAgency
import com.example.testui.model.Item
import com.example.testui.model.OrderAgency
import com.google.android.material.navigation.NavigationView


class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val items: MutableList<Item> = ArrayList()
        val agency1 = Agency("Tiến Thu", "200")
        items.add(Item(0, agency1))
        val contact1 = ContactAgency("33/61 Hương Lộ Ngọc Hiệp", "6:00 - 17:00", "0967070842")
        items.add(Item(1, contact1))
        val order1 = OrderAgency("Đặt lịch hẹn")
        items.add(Item(2, order1))

        binding.rvAgencyHome.adapter = AdapterHomeAgency(items)

        return binding.root
    }
}