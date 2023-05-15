package com.example.myvhc.admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import com.example.myvhc.R
import com.example.myvhc.admin.DashboardAdminActivity
import com.example.myvhc.admin.bottom_sheet.UpdateVehicleSheetFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityAdminVehicleDetailBinding
import com.example.myvhc.models.Vehicle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase

class AdminVehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vData = intent.getParcelableExtra<Vehicle>("vData")
        val vDataUpdate = intent.getParcelableExtra<Vehicle>("vDataUpdate")
        if (vDataUpdate != null) {
            displayVehicleData(vDataUpdate)
            intent.removeExtra("vDataUpdate")
        } else {
            vData?.let {
                displayVehicleData(it)
            }
        }

        binding.btnUpdate.setOnClickListener {
            showUpdateVehicleSheetFragment(vData)
        }
        binding.btnDelete.setOnClickListener {
            vData?.vehicleChassisNum?.let { deleteVehicle(it) }
        }
        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnExpand.setOnClickListener {
            if (binding.imageVehicle.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.bodyLayout, AutoTransition())
                binding.imageVehicle.visibility = View.VISIBLE
                binding.btnExpand.setImageResource(R.drawable.ic_expand)
            } else {
                TransitionManager.beginDelayedTransition(binding.bodyLayout, AutoTransition())
                binding.imageVehicle.visibility = View.GONE
                binding.btnExpand.setImageResource(R.drawable.ic_collapse)
            }
        }

    }

    private fun deleteVehicle(vehicleChassisNum: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("vehicles").child(vehicleChassisNum)
        dbRef.removeValue().addOnCompleteListener {
            val intent = Intent(this, DashboardAdminActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun displayVehicleData(vehicle: Vehicle) {
        with(binding) {
            txtVehicleBrand.text = vehicle.vehicleBrand
            txtVehicleModel.text = vehicle.vehicleModel
//            txtVehicleImg.text = vehicle.vehicleImg
            txtVehicleChassisNum.text = vehicle.vehicleChassisNum
            txtCylinderCapacity.text = vehicle.vehicleCylinderCap
            txtVehiclePrice.text = vehicle.vehiclePrice
        }
    }

    private fun showUpdateVehicleSheetFragment(vData: Vehicle?) {
        val bundle = Bundle().apply {
            putParcelable("vData", vData)
        }
        val fragment = UpdateVehicleSheetFragment().apply {
            arguments = bundle
        }
        fragment.show(supportFragmentManager, "newTaskTag")
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}


