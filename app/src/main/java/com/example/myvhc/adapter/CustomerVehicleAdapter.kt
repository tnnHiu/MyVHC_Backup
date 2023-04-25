package com.example.myvhc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.databinding.ItemCustomerVehicleBinding
import com.example.myvhc.databinding.ItemMyVehicleListBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class CustomerVehicleAdapter(
    private val cvList: ArrayList<CustomerVehicle>, private var vList: ArrayList<Vehicle>
) : RecyclerView.Adapter<CustomerVehicleAdapter.ViewHolder>() {

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
        return cvList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderBinding = holder.binding
        for (i in cvList) {
            if (vList[position].vehicleChassisNum == cvList[position].vehicleId) {
                holderBinding.txtVehicleModel.text = vList[position].vehicleModel
                holderBinding.txtVehicleLicensePlate.text = cvList[position].licensePlate
                holderBinding.txtVehicleCyclinderCapacity.text = vList[position].vehicleCylinderCap
                holderBinding.txtWarrantyInfo.text = cvList[position].purchaseDate
            }
        }
    }
}
