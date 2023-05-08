package com.example.myvhc.myVHCActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.myvhc.AgencyMapsActivity
import com.example.myvhc.databinding.ActivityVehicleDetailBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getIntent = intent
        val bundle = getIntent.extras
        val cvData = bundle?.getParcelable<CustomerVehicle>("cvData")
        val vData = bundle?.getParcelable<Vehicle>("vData")
//        val agency

        if (vData != null && cvData != null) {
            Glide.with(this).load(vData.vehicleImg).into(binding.imgVehicle)
            binding.txtVehicleModel.text = vData.vehicleModel.toString()
            binding.txtVehicleBrand.text = vData.vehicleBrand.toString()
            binding.txtLicensePlate.text = cvData.licensePlate.toString()
            binding.txtVehicleChassisNum.text = vData.vehicleChassisNum.toString()
            binding.txtCyclinderCapacity.text = vData.vehicleCylinderCap.toString()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnOrderService.setOnClickListener {
            val intent = Intent(this, AgencyMapsActivity::class.java)
            val bundleAgencyMapsActivity = Bundle()
            bundleAgencyMapsActivity.putParcelable("cvData", cvData)
            bundleAgencyMapsActivity.putParcelable("vData", vData)
            intent.putExtras(bundleAgencyMapsActivity)
            startActivity(intent)
        }
    }
}