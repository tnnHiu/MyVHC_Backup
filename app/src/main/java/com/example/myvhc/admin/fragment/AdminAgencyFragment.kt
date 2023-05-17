package com.example.myvhc.admin.fragment

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
import com.example.myvhc.admin.activity.AdminAgencyDetailActivity
import com.example.myvhc.admin.activity.AdminVehicleDetailActivity
import com.example.myvhc.admin.adapter.AdminAgencyAdapter
import com.example.myvhc.admin.adapter.AdminVehicleAdapter
import com.example.myvhc.admin.bottom_sheet.AddAgencySheetFragment
import com.example.myvhc.databinding.FragmentAdminAgencyBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.AgencyViewModel
import com.example.myvhc.viewmodels.VehicleViewModel

class AdminAgencyFragment : Fragment() {

    private lateinit var binding: FragmentAdminAgencyBinding
    private lateinit var agencyVM: AgencyViewModel
    private lateinit var mAdapter: AdminAgencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminAgencyBinding.inflate(layoutInflater, container, false)
        getData()
        binding.btnAdd.setOnClickListener {
            AddAgencySheetFragment().show(childFragmentManager, "newTaskTag")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    private fun getData() {
        agencyVM = ViewModelProvider(this)[AgencyViewModel::class.java]
        agencyVM.agencyListSize.observe(viewLifecycleOwner, Observer {
            val agencyData = agencyVM.agencyList.value!!
            removeDuplicatesById(agencyData)
            setupRecyclerView(agencyData)

        })
    }

    private fun setupRecyclerView(agencyData: ArrayList<Agency>) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = AdminAgencyAdapter(agencyData)
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : AdminAgencyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(requireContext(), AdminAgencyDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("agencyData", agencyData[position])
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })

    }

    private fun removeDuplicatesById(list: ArrayList<Agency>) {
        val set = HashSet<String>()
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val agency = iterator.next()
            if (!agency.agencyId?.let { set.add(it) }!!) {
                iterator.remove()
            }
        }
    }
}