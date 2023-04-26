package com.example.myvhc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.ItemCustomerVehicleBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class CustomerVehicleAdapter(private val dataSorted: List<Pair<CustomerVehicle?, Vehicle>>) :
    RecyclerView.Adapter<CustomerVehicleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCustomerVehicleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCustomerVehicleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSorted.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderBinding = holder.binding
        holderBinding.txtVehicleModel.text = dataSorted[position].second.vehicleModel
        holderBinding.txtVehicleLicensePlate.text = dataSorted[position].first?.licensePlate
        holderBinding.txtVehicleCyclinderCapacity.text =
            dataSorted[position].second.vehicleCylinderCap
        holderBinding.txtWarrantyInfo.text = dataSorted[position].first?.purchaseDate
    }
}
