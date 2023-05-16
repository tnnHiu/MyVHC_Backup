package com.example.myvhc.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.ItemAdminServiceBinding
import com.example.myvhc.models.ServiceBookingForm

class AdminServiceAdapter(
    private val data: ArrayList<ServiceBookingForm>
) : RecyclerView.Adapter<AdminServiceAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    inner class ViewHolder(
        val binding: ItemAdminServiceBinding, clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val view = ItemAdminServiceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(
            view, mListener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtAgencyName.text = data[position].agencyId
    }


}