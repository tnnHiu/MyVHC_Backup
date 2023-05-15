package com.example.myvhc.admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myvhc.R
import com.example.myvhc.admin.bottom_sheet.UpdateVehicleSheetFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityAdminVehicleDetailBinding
import com.example.myvhc.models.Vehicle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AdminVehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val getIntent = intent
        val bundle = getIntent.extras
        val vData = bundle?.getParcelable<Vehicle>("vData")
        if (vData != null) {
            binding.txtVehicleBrand.text = vData.vehicleBrand.toString()
            binding.txtVehicleModel.text = vData.vehicleModel.toString()
            binding.txtVehicleImg.text = vData.vehicleImg.toString()
//            Glide.with(this).load(vData.vehicleImg).into(binding.txtVehicleImg)
            binding.txtVehicleChassisNum.text = vData.vehicleChassisNum.toString()
            binding.txtCylinderCapacity.text = vData.vehicleCylinderCap.toString()
            binding.txtVehiclePrice.text = vData.vehiclePrice.toString()
        }


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