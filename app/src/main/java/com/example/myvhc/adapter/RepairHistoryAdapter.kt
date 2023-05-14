package com.example.myvhc.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.ItemRepairHistoryBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.ServiceBookingForm


class RepairHistoryAdapter(
    private val data: List<Pair<ServiceBookingForm?, Agency>>
) : RecyclerView.Adapter<RepairHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemRepairHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RepairHistoryAdapter.ViewHolder {
        val view = ItemRepairHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderBinding = holder.binding
        holderBinding.txtAgencyName.text = data[position].second.agencyName
        holderBinding.txrDateRepair.text = data[position].first?.serviceDate
    }

    override fun getItemCount(): Int {

        return data.size
    }
}