package com.example.myvhc.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myvhc.R
import com.example.myvhc.adapter.AdapterViewPager
import com.example.myvhc.admin.Fragment.AdminAgencyFragment
import com.example.myvhc.admin.Fragment.AdminCustomerFragment
import com.example.myvhc.admin.Fragment.AdminCustomerVHCFragment
import com.example.myvhc.admin.Fragment.AdminServiceFragment
import com.example.myvhc.admin.Fragment.AdminVehicleFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityDashboardAdminBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    private val fragmentArrayList = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            signOut()
        }

        binding.userName.text = FirebaseAuth.getInstance().currentUser?.displayName

        fragmentArrayList.add(AdminVehicleFragment())
        fragmentArrayList.add(AdminAgencyFragment())
        fragmentArrayList.add(AdminCustomerFragment())
        fragmentArrayList.add(AdminCustomerVHCFragment())
        fragmentArrayList.add(AdminServiceFragment())

        binding.pagerMain.adapter = AdapterViewPager(this, fragmentArrayList)

        binding.pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = when (position) {
                    0 -> R.id.ad_vehicle
                    1 -> R.id.ad_agency
                    2 -> R.id.ad_customer
                    3 -> R.id.ad_customer_vehicle
                    else -> R.id.ad_service
                }
                super.onPageSelected(position)
            }
        })

        binding.bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            binding.pagerMain.currentItem = when (item.itemId) {
                R.id.ad_vehicle -> 0
                R.id.ad_agency -> 1
                R.id.ad_customer-> 2
                R.id.ad_customer_vehicle -> 3
                else -> 4
            }
            true
        })

    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}