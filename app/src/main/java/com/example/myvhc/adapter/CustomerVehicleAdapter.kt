package com.example.myvhc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.myvhc.databinding.ItemCustomerVehicleBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class CustomerVehicleAdapter(
    private val dataSorted: List<Pair<CustomerVehicle?, Vehicle>>
) : RecyclerView.Adapter<CustomerVehicleAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    inner class ViewHolder(
        val binding: ItemCustomerVehicleBinding, clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun loadImg(): RequestManager {
            return Glide.with(itemView)
        }

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCustomerVehicleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view, mListener)
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
