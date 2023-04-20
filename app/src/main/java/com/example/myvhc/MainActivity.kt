package com.example.myvhc


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myvhc.adapter.AdapterViewPager
import com.example.myvhc.databinding.ActivityMainBinding
import com.example.myvhc.drawerActivity.GuaranActivity
import com.example.myvhc.fragment_main.FragmentAgency
import com.example.myvhc.fragment_main.FragmentHome
import com.example.myvhc.fragment_main.FragmentMail
import com.example.myvhc.fragment_main.FragmentProduct
import com.example.myvhc.fragment_main.FragmentVehicle
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

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

        //hiện các trang fragment main ở pager
        binding.pagerMain.adapter = adapterViewPager

        //lướt để đổi fragment
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

        //code để bấm vào nút menu sẽ hiện drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        findViewById<View>(R.id.imgMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_guaran -> {
                    startActivity(Intent(this, GuaranActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_drawer, menu)
        return super.onCreateOptionsMenu(menu)
    }

}