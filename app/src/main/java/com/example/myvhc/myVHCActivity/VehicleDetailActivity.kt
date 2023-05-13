package com.example.myvhc.myVHCActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myvhc.AgencyMapsActivity
import com.example.myvhc.adapter.RepairHistoryAdapter
import com.example.myvhc.databinding.ActivityVehicleDetailBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.ServiceBookingForm
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.RepairHistoryViewModel

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleDetailBinding
    private lateinit var historyViewModel: RepairHistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getIntent = intent
        val bundle = getIntent.extras
        val cvData = bundle?.getParcelable<CustomerVehicle>("cvData")
        val vData = bundle?.getParcelable<Vehicle>("vData")

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
        val vehicleId = cvData?.vehicleId.toString()
        getData(vehicleId)
    }

    private fun getData(vehicleId: String) {
        historyViewModel = ViewModelProvider(this)[RepairHistoryViewModel::class.java]
        historyViewModel.rHListSize.observe(this, Observer {
            val rHList = historyViewModel.rHList.value!!
            val agencyList = historyViewModel.agencyList.value!!
            val dataSorted = arrayDataSynchronization(rHList, agencyList, vehicleId)
            setupRecyclerView(dataSorted, vehicleId)
        })

    }


    private fun setupRecyclerView(
        data: List<Pair<ServiceBookingForm?, Agency>>, vehicleId: String
    ) {
        val recyclerView = binding.rvHistoryRepair
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RepairHistoryAdapter(vehicleId, data)
    }


    private fun arrayDataSynchronization(
        rHList: ArrayList<ServiceBookingForm>, agencyList: ArrayList<Agency>, vehicleId: String
    ): List<Pair<ServiceBookingForm?, Agency>> {
        val filteredRH = rHList.filter { it.vehicleId == vehicleId && it.status == "1" }
        val rHIds = filteredRH.map { it.agencyId }.toHashSet()
        val filteredVData = agencyList.filter { rHIds.contains(it.agencyId) }
        val combinedList = filteredVData.map { agency ->
            val matchingRH = filteredRH.find { it.agencyId == agency.agencyId }
            Pair(matchingRH, agency)
        }
        return combinedList.sortedWith(compareBy { it.first?.agencyId })
    }

}