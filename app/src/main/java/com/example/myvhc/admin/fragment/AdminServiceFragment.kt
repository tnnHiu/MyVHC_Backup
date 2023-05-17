package com.example.myvhc.admin.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvhc.admin.activity.AdminServiceDetailActivity
import com.example.myvhc.admin.adapter.AdminServiceAdapter
import com.example.myvhc.databinding.FragmentAdminServiceBinding
import com.example.myvhc.models.ServiceBookingForm
import com.example.myvhc.viewmodels.ServiceBookingFormViewModel

class AdminServiceFragment : Fragment() {

    private lateinit var binding: FragmentAdminServiceBinding
    private lateinit var serviceVM: ServiceBookingFormViewModel
    private lateinit var mAdapter: AdminServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminServiceBinding.inflate(layoutInflater, container, false)
        getData()
        return binding.root
    }

    private fun getData() {
        serviceVM = ViewModelProvider(this)[ServiceBookingFormViewModel::class.java]
        serviceVM.sbfListSize.observe(viewLifecycleOwner, Observer {
            val sbfData = serviceVM.sbfList.value!!
            val sbfDataCheck = filterByStatus(sbfData)
            setupRecyclerView(sbfDataCheck)

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun setupRecyclerView(sbfData: ArrayList<ServiceBookingForm>) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = AdminServiceAdapter(sbfData)
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : AdminServiceAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(requireContext(), AdminServiceDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("sbfData", sbfData[position])
                intent.putExtras(bundle)
                startActivity(intent)

            }
        })

    }

    private fun filterByStatus(sbfData: ArrayList<ServiceBookingForm>): ArrayList<ServiceBookingForm> {
        return sbfData.filter { it.status == "0" } as ArrayList<ServiceBookingForm>
    }

}