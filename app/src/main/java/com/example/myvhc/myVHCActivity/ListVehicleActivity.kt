package com.example.myvhc.myVHCActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvhc.R
import com.example.myvhc.adapter.ChoosingVehicleAdapter
import com.example.myvhc.adapter.CustomerVehicleAdapter
import com.example.myvhc.databinding.ActivityListVehicleBinding
import com.example.myvhc.databinding.ActivityVehicleDetailBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.CustomerVehicleViewModel
import com.example.myvhc.viewmodels.VehicleViewModel

class ListVehicleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListVehicleBinding
    private lateinit var customerVehicleViewModel: CustomerVehicleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()




        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        customerVehicleViewModel = ViewModelProvider(this)[CustomerVehicleViewModel::class.java]
        customerVehicleViewModel.vListSize.observe(this, Observer {
            val cvData = customerVehicleViewModel.cvList.value!!
            val vData = customerVehicleViewModel.vList.value!!
            val dataSorted = arrayDataSynchronization(cvData, vData)
            val recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            val mAdapter = ChoosingVehicleAdapter(dataSorted)
            recyclerView.adapter = mAdapter
            mAdapter.setOnItemClickListener(object : ChoosingVehicleAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@ListVehicleActivity, VehicleDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        })
    }

    private fun arrayDataSynchronization(
        cvData: ArrayList<CustomerVehicle>, vData: ArrayList<Vehicle>
    ): List<Pair<CustomerVehicle?, Vehicle>> {

        // Tạo list kết hợp của vData và cvData theo vehicleId = vehicleChassisNum
        val combinedList = vData.map { vehicle ->
            val matchingCustomerVehicle = cvData.find { it.vehicleId == vehicle.vehicleChassisNum }
            Pair(matchingCustomerVehicle, vehicle)
        }
        //   .filter { it.first != null }
        // Bỏ những phần tử có vehicleId không tồn tại trong danh sách customerVehicles

        //  Sắp xếp và trả về list mới theo vehicleId
        return combinedList.sortedWith(compareBy { it.first?.vehicleId })
    }
}