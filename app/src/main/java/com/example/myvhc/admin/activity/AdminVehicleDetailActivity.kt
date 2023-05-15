package com.example.myvhc.admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.admin.bottom_sheet.UpdateVehicleSheetFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityAdminVehicleDetailBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AdminVehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            UpdateVehicleSheetFragment().show(supportFragmentManager, "newTaskTag")
        }
        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}