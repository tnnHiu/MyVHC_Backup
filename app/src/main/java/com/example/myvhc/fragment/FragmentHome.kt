package com.example.myvhc.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvhc.adapter.AdapterHomeAgency
import com.example.myvhc.admin.DashboardAdminActivity
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.myVHCActivity.AddMyVehicleActivity
import com.example.myvhc.viewmodels.AgencyViewModel


class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding
    private lateinit var agencyVM: AgencyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.test.setOnClickListener {
            startActivity(Intent(context, AddMyVehicleActivity::class.java))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        agencyVM = ViewModelProvider(this)[AgencyViewModel::class.java]
        agencyVM.agencyListSize.observe(viewLifecycleOwner, Observer {
            val agency = agencyVM.agencyList.value!!
            val recyclerView = binding.rvAgencyHome
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = AdapterHomeAgency(agency)
        })

    }
}