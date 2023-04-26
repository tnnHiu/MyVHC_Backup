package com.example.myvhc.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.ViewTarget
import com.example.myvhc.databinding.ItemCustomerVehicleBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class CustomerVehicleAdapter(
    private val dataSorted: List<Pair<CustomerVehicle?, Vehicle>>
) : RecyclerView.Adapter<CustomerVehicleAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemCustomerVehicleBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun loadImg(): RequestManager {
            return Glide.with(itemView)
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
        holder.loadImg().load(dataSorted[position].second.vehicleImg).into(holderBinding.imgVehicle)
        holderBinding.txtVehicleModel.text = dataSorted[position].second.vehicleModel
        holderBinding.txtVehicleLicensePlate.text = dataSorted[position].first?.licensePlate
        holderBinding.txtVehicleCyclinderCapacity.text =
            dataSorted[position].second.vehicleCylinderCap
        holderBinding.txtWarrantyInfo.text = dataSorted[position].first?.purchaseDate
    }
}
