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
import com.example.myvhc.fragment.FragmentVehicle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var auth = FirebaseAuth.getInstance().currentUser
    private lateinit var binding: ActivityMainBinding
    private val fragmentArrayList = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentArrayList.add(FragmentHome())
        fragmentArrayList.add(FragmentVehicle())
        fragmentArrayList.add(FragmentAgency())
        fragmentArrayList.add(FragmentMail())
        Log.i("aaa", auth.toString())
        binding.pagerMain.adapter = AdapterViewPager(this, fragmentArrayList)

        binding.pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = when (position) {
                    0 -> R.id.home
                    1 -> R.id.vehicle
                    2 -> R.id.agency
                    else -> R.id.mail
                }
                super.onPageSelected(position)
            }
        })

        binding.bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            binding.pagerMain.currentItem = when (item.itemId) {
                R.id.home -> 0
                R.id.vehicle -> 1
                R.id.agency -> 2
                else -> 3
            }
            true
        })

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        findViewById<View>(R.id.imgMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val userInTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.userName)
        userInTextView.text = FirebaseAuth.getInstance().currentUser?.displayName

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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
        menuInflater.inflate(R.menu.menu_drawer, menu)
        return true
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}
