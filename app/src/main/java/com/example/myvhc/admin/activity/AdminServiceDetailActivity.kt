package com.example.myvhc.admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.admin.bottom_sheet.UpdateServiceSheetFragment
import com.example.myvhc.authActivity.LogInActivity
import com.example.myvhc.databinding.ActivityAdminServiceDetailBinding
import com.example.myvhc.models.ServiceBookingForm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AdminServiceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminServiceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminServiceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sbfData = intent.getParcelableExtra<ServiceBookingForm>("sbfData")
        val sbfDataUpdate = intent.getParcelableExtra<ServiceBookingForm>("sbfDataUpdate")
        if (sbfDataUpdate != null) {
            displaySBFData(sbfDataUpdate)
            intent.removeExtra("sbfDataUpdate")
        } else {
            if (sbfData != null) {
                displaySBFData(sbfData)
            }
        }

        binding.btnUpdate.setOnClickListener {
            showUpdateSBFSheetFragment(sbfData)
        }

        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun displaySBFData(sbf: ServiceBookingForm) {
        with(binding) {
            txtAgencyId.text = sbf.agencyId
            txtCreationDate.text = sbf.creationDate
            txtCustomerId.text = sbf.customerId
            txtDescribe.text = sbf.describe
            txtBookedDate.text = sbf.serviceDate
            txtBookedHours.text = sbf.serviceHours
            txtStatus.text = sbf.status
            txtVehicleId.text = sbf.vehicleId
        }
    }

    private fun showUpdateSBFSheetFragment(sbfData: ServiceBookingForm?) {
        val bundle = Bundle().apply {
            putParcelable("sbfData", sbfData)
        }
        val fragment = UpdateServiceSheetFragment().apply {
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