package com.example.myvhc


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myvhc.adapter.AdapterViewPager
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityMainBinding
import com.example.myvhc.drawerActivity.GuaranActivity
import com.example.myvhc.fragment.FragmentAgency
import com.example.myvhc.fragment.FragmentHome
import com.example.myvhc.fragment.FragmentMail
import com.example.myvhc.fragment.FragmentProduct
import com.example.myvhc.fragment.FragmentVehicle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentArrayList = ArrayList<Fragment>()
    private var auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("cccp", "${auth?.uid}")


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
        val headerView = navigationView.getHeaderView(0)
        val userInTextView = headerView.findViewById<TextView>(R.id.userName)
        userInTextView.text = auth?.displayName

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_guaran -> {
                    startActivity(Intent(this, GuaranActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.END)
                }

                R.id.nav_logout -> {
                    signOut()
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

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }

}