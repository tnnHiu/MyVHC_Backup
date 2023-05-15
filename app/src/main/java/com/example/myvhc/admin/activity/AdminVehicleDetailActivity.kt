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

        val vData = intent.getParcelableExtra<Vehicle>("vData")
        vData?.let {
            displayVehicleData(it)
        }

        binding.btnUpdate.setOnClickListener {
            showUpdateVehicleSheetFragment()
        }
        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayVehicleData(vehicle: Vehicle) {
        with(binding) {
            txtVehicleBrand.text = vehicle.vehicleBrand
            txtVehicleModel.text = vehicle.vehicleModel
            txtVehicleImg.text = vehicle.vehicleImg
//            Glide.with(this).load(vehicle.vehicleImg).into(txtVehicleImg)
            txtVehicleChassisNum.text = vehicle.vehicleChassisNum
            txtCylinderCapacity.text = vehicle.vehicleCylinderCap
            txtVehiclePrice.text = vehicle.vehiclePrice
        }
    }

    private fun showUpdateVehicleSheetFragment() {
        UpdateVehicleSheetFragment().show(supportFragmentManager, "newTaskTag")
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}
