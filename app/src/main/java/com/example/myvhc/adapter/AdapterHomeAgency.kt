package com.example.myvhc.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.databinding.FragmentHomeBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.CustomerVehicle


class AdapterHomeAgency(private val customerVehicleList: ArrayList<CustomerVehicle>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(var binding: FragmentHomeBinding) : RecyclerView.ViewHolder(binding.root) {}
}