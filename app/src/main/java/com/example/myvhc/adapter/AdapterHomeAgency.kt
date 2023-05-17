package com.example.myvhc.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.ItemAdminAgencyBinding
import com.example.myvhc.models.Agency


class AdapterHomeAgency(private val agencyList: ArrayList<Agency>) :
    RecyclerView.Adapter<AdapterHomeAgency.ViewHolder>() {
    inner class ViewHolder(var binding: ItemAdminAgencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAdminAgencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return agencyList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.agencyId.text = agencyList[position].agencyName.toString()
    }


}