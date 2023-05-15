package com.example.myvhc.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.myvhc.databinding.ItemAdminVehicleBinding
import com.example.myvhc.models.Vehicle

class AdminVehicleAdapter(
    private val data: ArrayList<Vehicle>
) : RecyclerView.Adapter<AdminVehicleAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    inner class ViewHolder(
        val binding: ItemAdminVehicleBinding,
        clickListener: OnItemClickListener
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
        val view = ItemAdminVehicleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(
            view,
            mListener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadImg().load(data[position].vehicleImg).into((holder.binding.imgVehicle))
        holder.binding.vehicleChassisNum.text = data[position].vehicleModel
    }
}