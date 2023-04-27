package com.example.myvhc.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.myvhc.databinding.ItemChoosingVehicleBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle

class ChoosingVehicleAdapter(
    private val dataSorted: List<Pair<CustomerVehicle?, Vehicle>>
) : RecyclerView.Adapter<ChoosingVehicleAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    inner class ViewHolder(
        val binding: ItemChoosingVehicleBinding, clickListener: OnItemClickListener
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
        val view = ItemChoosingVehicleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return dataSorted.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.loadImg().load(dataSorted[position].second.vehicleImg)
            .into(holder.binding.imgVehicle)
        holder.binding.txtVehicleModel.text = dataSorted[position].second.vehicleModel
        holder.binding.txtVehicleLicensePlate.text = dataSorted[position].first?.licensePlate
    }

}