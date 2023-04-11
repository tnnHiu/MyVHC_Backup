package com.example.myvhc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myvhc.adapter.AdapterViewPager
import com.example.myvhc.databinding.ActivityMainBinding
import com.example.testui.fragment_main.*
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentArrayList = ArrayList<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentArrayList.add(FragmentHome())
        fragmentArrayList.add(FragmentVehicle())
        fragmentArrayList.add(FragmentProduct())
        fragmentArrayList.add(FragmentAgency())
        fragmentArrayList.add(FragmentMail())

        val adapterViewPager = AdapterViewPager(this, fragmentArrayList)

        binding.pagerMain.adapter = adapterViewPager

        binding.pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNav.selectedItemId = R.id.home
                    1 -> binding.bottomNav.selectedItemId = R.id.vehicle
                    2 -> binding.bottomNav.selectedItemId = R.id.product
                    3 -> binding.bottomNav.selectedItemId = R.id.agency
                    4 -> binding.bottomNav.selectedItemId = R.id.mail
                }
                super.onPageSelected(position)
            }
        })

        binding.bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> binding.pagerMain.currentItem = 0
                R.id.vehicle -> binding.pagerMain.currentItem = 1
                R.id.product -> binding.pagerMain.currentItem = 2
                R.id.agency -> binding.pagerMain.currentItem = 3
                R.id.mail -> binding.pagerMain.currentItem = 4
            }
            true
        })

    }
}