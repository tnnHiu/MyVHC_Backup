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
import com.example.myvhc.models.ServiceBookingForm
import com.example.myvhc.models.Vehicle
import com.example.myvhc.serviceActivity.ServiceActivity
import com.example.myvhc.viewmodels.CustomerVehicleViewModel
import com.example.myvhc.viewmodels.VehicleViewModel

class ListVehicleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListVehicleBinding
    private lateinit var customerVehicleViewModel: CustomerVehicleViewModel
    private lateinit var mAdapter: ChoosingVehicleAdapter

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
            setupRecyclerView(dataSorted)
        })
    }

    private fun setupRecyclerView(dataSorted: List<Pair<CustomerVehicle?, Vehicle>>) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = ChoosingVehicleAdapter(dataSorted)
        recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : ChoosingVehicleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val getIntent = intent
                val bundle = getIntent.extras
                val agencyId = bundle?.getString("agencyId")
                val agencyName = bundle?.getString("agencyName")
                val agencyAddress = bundle?.getString("agencyAddress")
                val intent = Intent(this@ListVehicleActivity, ServiceActivity::class.java)
                val bundleServiceActivity = Bundle()
                val cvDataSorted = dataSorted[position].first
                val vDataSorted = dataSorted[position].second
                bundleServiceActivity.putString("agencyId", agencyId)
                bundleServiceActivity.putString("agencyName", agencyName)
                bundleServiceActivity.putString("agencyAddress", agencyAddress)
                bundleServiceActivity.putParcelable("cvData", cvDataSorted)
                bundleServiceActivity.putParcelable("vData", vDataSorted)
                intent.putExtras(bundleServiceActivity)
                startActivity(intent)
            }
        })
    }


    private fun arrayDataSynchronization(
        cvData: ArrayList<CustomerVehicle>, vData: ArrayList<Vehicle>
    ): List<Pair<CustomerVehicle?, Vehicle>> {

        // Tạo một HashSet chứa tất cả các vehicleId từ danh sách customerVehicles
        val cvVehicleIds = cvData.map { it.vehicleId }.toHashSet()

        // Lọc danh sách vehicleData chỉ giữ lại những phần tử có vehicleId tồn tại trong danh sách customerVehicles
        val filteredVData = vData.filter { cvVehicleIds.contains(it.vehicleChassisNum) }

        // Tạo list kết hợp của filteredVData và cvData theo vehicleId = vehicleChassisNum
        val combinedList = filteredVData.map { vehicle ->
            val matchingCustomerVehicle = cvData.find { it.vehicleId == vehicle.vehicleChassisNum }
            Pair(matchingCustomerVehicle, vehicle)
        }

        // Sắp xếp và trả về list mới theo vehicleId
        return combinedList.sortedWith(compareBy { it.first?.vehicleId })
    }
}