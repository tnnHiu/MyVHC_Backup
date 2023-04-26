package com.example.myvhc.fragment_main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvhc.adapter.CustomerVehicleAdapter
import com.example.myvhc.databinding.FragmentVehicleBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.myVHCActivity.AddMyVehicleActivity
import com.example.myvhc.viewmodels.CustomerVehicleViewModel


class FragmentVehicle : Fragment() {

    private lateinit var binding: FragmentVehicleBinding
    private lateinit var customerVehicleViewModel: CustomerVehicleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVehicleBinding.inflate(layoutInflater, container, false)

        getData()
        binding.btnAddVehicle.setOnClickListener {
            startActivity(Intent(context, AddMyVehicleActivity::class.java))
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    private fun getData() {
        customerVehicleViewModel = ViewModelProvider(this)[CustomerVehicleViewModel::class.java]
        customerVehicleViewModel.vListSize.observe(viewLifecycleOwner, Observer {
            val cvData = customerVehicleViewModel.cvList.value!!
            val vData = customerVehicleViewModel.vList.value!!
            val dataSorted = arrayDataSynchronization(cvData, vData)
            val recyclerView = binding.myVehicleRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = CustomerVehicleAdapter(dataSorted)
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

