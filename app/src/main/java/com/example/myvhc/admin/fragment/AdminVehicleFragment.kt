package com.example.myvhc.admin.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvhc.admin.adapter.AdminVehicleAdapter
import com.example.myvhc.admin.bottom_sheet.AddVehicleSheetFragment
import com.example.myvhc.databinding.FragmentAdminVehicleBinding
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.VehicleViewModel

class AdminVehicleFragment : Fragment() {

    private lateinit var binding: FragmentAdminVehicleBinding
    private lateinit var vehicleVM: VehicleViewModel
    private lateinit var mAdapter: AdminVehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminVehicleBinding.inflate(layoutInflater, container, false)

        binding.btnAdd.setOnClickListener {
            AddVehicleSheetFragment().show(childFragmentManager, "newTaskTag")
        }
        getData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    private fun getData() {
        vehicleVM = ViewModelProvider(this)[VehicleViewModel::class.java]
        vehicleVM.vListSize.observe(viewLifecycleOwner, Observer {
            val vData = vehicleVM.vList.value!!
            removeDuplicatesByChassisNumber(vData)
            setupRecyclerView(vData)
            Log.i("checkData", vData.toString())
        })
    }

    private fun setupRecyclerView(vData: ArrayList<Vehicle>) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = AdminVehicleAdapter(vData)
        recyclerView.adapter = mAdapter

    }


    private fun removeDuplicatesByChassisNumber(list: ArrayList<Vehicle>) {
        val set = HashSet<String>()
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val vehicle = iterator.next()
            if (!vehicle.vehicleChassisNum?.let { set.add(it) }!!) {
                iterator.remove()
            }
        }
    }


}