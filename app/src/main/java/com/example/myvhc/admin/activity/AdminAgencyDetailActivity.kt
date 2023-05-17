package com.example.myvhc.admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.admin.DashboardAdminActivity
import com.example.myvhc.admin.bottom_sheet.UpdateAgencySheetFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityAdminAgencyDetailBinding
import com.example.myvhc.models.Agency
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase

class AdminAgencyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAgencyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAgencyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val agencyData = intent.getParcelableExtra<Agency>("agencyData")
        val agencyDataUpdate = intent.getParcelableExtra<Agency>("agencyDataUpdate")
        if (agencyDataUpdate != null) {
            displayVehicleData(agencyDataUpdate)
            intent.removeExtra("agencyDataUpdate")
        } else {
            agencyData?.let {
                displayVehicleData(it)
            }
        }
        binding.btnUpdate.setOnClickListener {
            showUpdateVehicleSheetFragment(agencyData)
        }
        binding.btnDelete.setOnClickListener {
            agencyData?.agencyId?.let { deleteAgency(it) }
        }


        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showUpdateVehicleSheetFragment(agencyData: Agency?) {
        val bundle = Bundle().apply {
            putParcelable("agencyData", agencyData)
        }
        val fragment = UpdateAgencySheetFragment().apply {
            arguments = bundle
        }
        fragment.show(supportFragmentManager, "newTaskTag")
    }

    private fun deleteAgency(agencyId: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("agencies").child(agencyId)
        dbRef.removeValue().addOnCompleteListener {
            val intent = Intent(this, DashboardAdminActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun displayVehicleData(agencyData: Agency) {
        with(binding) {
            txtAgencyAddress.text = agencyData.agencyAddress
            txtAgencyId.text = agencyData.agencyId
            txtAgencyLnt.text = agencyData.agencyLatitude.toString()
            txtAgencyLng.text = agencyData.agencyLatitude.toString()
            txtAgencyName.text = agencyData.agencyName
            txtAgencyPhone.text = agencyData.agencyPhoneNum
            txtAgencyWorkTime.text = agencyData.agencyWorkTime
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