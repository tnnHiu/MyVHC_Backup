package com.example.myvhc.serviceActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvhc.R
import com.example.myvhc.adapter.ChoosingVehicleAdapter
import com.example.myvhc.databinding.ActivityAgencyDetailBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.myVHCActivity.ListVehicleActivity

class AgencyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgencyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgencyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getIntent = intent
        val bundle = getIntent.extras
        val cvData = bundle?.getParcelable<CustomerVehicle>("cvData")
        val vData = bundle?.getParcelable<Vehicle>("vData")
        val agencyId = bundle?.getString("agencyId")
        val agencyName = bundle?.getString("agencyName")
        val agencyAddress = bundle?.getString("agencyAddress")
        val agencyPhone = bundle?.getString("agencyPhoneNum")
        val agencyWorkTime = bundle?.getString("agencyWorkTime")

        binding.txtAgencyName.text = agencyName
        binding.txtAgencyAddress.text = agencyAddress
        binding.txtAgencyPhone.text = agencyPhone
        binding.txtAgencyWorkTime.text = agencyWorkTime



        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnOrder.setOnClickListener {
            if (vData != null) {
                val intent = Intent(this, ServiceActivity::class.java)
                val bundleServiceActivity = Bundle()
                bundleServiceActivity.putString("agencyId", agencyId)
                bundleServiceActivity.putString("agencyName", agencyName)
                bundleServiceActivity.putString("agencyAddress", agencyAddress)
                bundleServiceActivity.putParcelable(
                    "cvData", cvData
                )
                bundleServiceActivity.putParcelable("vData", vData)
                intent.putExtras(bundleServiceActivity)
                startActivity(intent)
            } else {
                val intent = Intent(this, ListVehicleActivity::class.java)
                val bundleListVehicleActivity = Bundle()
                bundleListVehicleActivity.putString("agencyId", agencyId)
                bundleListVehicleActivity.putString("agencyName", agencyName)
                bundleListVehicleActivity.putString("agencyAddress", agencyAddress)
                intent.putExtras(bundleListVehicleActivity)
                startActivity(intent)
            }
        }

    }
}