package com.example.myvhc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvhc.R
import com.example.testui.model.Item
import com.example.testui.model.Agency
import com.example.testui.model.ContactAgency
import com.example.testui.model.OrderAgency


class AdapterHomeAgency(private val items: List<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            AgencyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_agency,
                    parent,
                    false
                )
            )
        } else if (viewType == 1) {
            ContactViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_agency_contact,
                    parent,
                    false
                )
            )
        } else {
            OrderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_agency_order,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            val agency = items[position].`object` as Agency
            (holder as AgencyViewHolder).setAgencyData(agency)
        } else if (getItemViewType(position) == 1) {
            val contactAgency = items[position].`object` as ContactAgency
            (holder as ContactViewHolder).setContactAgencyData(contactAgency)
        } else {
            val order = items[position].`object` as OrderAgency
            (holder as OrderViewHolder).setOrderAgencyData(order)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    internal class AgencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameAgency: TextView
        private val numEvaAgency: TextView

        init {
            nameAgency = itemView.findViewById(R.id.txtNameAgency)
            numEvaAgency = itemView.findViewById(R.id.txtNumEva)
        }

        fun setAgencyData(agency: Agency){
            nameAgency.text = agency.agencyName
            numEvaAgency.text = agency.agencyNumEva
        }
    }

    internal class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleOrder: TextView

        init {
            titleOrder = itemView.findViewById(R.id.txtOrder)
        }

        fun setOrderAgencyData(orderAgency: OrderAgency){
            titleOrder.text = orderAgency.orderTitle
        }
    }

    internal class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressContact: TextView
        private val timeContact: TextView
        private val phoneContact: TextView

        init {
            addressContact = itemView.findViewById(R.id.txtAddressAgency)
            timeContact = itemView.findViewById(R.id.txtTimeAgency)
            phoneContact = itemView.findViewById(R.id.txtPhoneAgency)
        }

        fun setContactAgencyData(contactAgency: ContactAgency){
            addressContact.text = contactAgency.contactAddress
            timeContact.text = contactAgency.contactTime
            phoneContact.text = contactAgency.contactPhone
        }
    }


}