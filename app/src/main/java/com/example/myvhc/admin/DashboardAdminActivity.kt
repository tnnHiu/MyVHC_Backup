package com.example.myvhc.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myvhc.R
import com.example.myvhc.databinding.ActivityDashboardAdminBinding

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerAdLayout)
        findViewById<View>(R.id.imgMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

    }
}