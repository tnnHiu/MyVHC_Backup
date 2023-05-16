package com.example.myvhc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvhc.models.Agency
import com.example.myvhc.models.ServiceBookingForm
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ServiceBookingFormViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance()
    private val sbfRef = dbRef.getReference("service_booking_form")
    private val agencyListRef = dbRef.getReference("agencies")
    val sbfList = MutableLiveData<ArrayList<ServiceBookingForm>>(ArrayList())
    var agencyList = MutableLiveData<ArrayList<Agency>>(ArrayList())
    val sbfListSize = MutableLiveData<Int>()

    init {
        getData()
    }

    private fun getData() {
        sbfRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    sbfList.value?.clear()
                    for (data in snapshot.children) {
                        val dataHR = data.getValue(ServiceBookingForm::class.java)
                        if (dataHR != null) {
                            sbfList.value?.add(dataHR)
                            getAgencyList()


                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getAgencyList() {
        agencyListRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    agencyList.value?.clear()
                    for (agency in snapshot.children) {
                        val dataAgency = agency.getValue(Agency::class.java)
                        if (dataAgency != null) {
                            agencyList.value?.add(dataAgency)
                        }
                    }
                    sbfListSize.value = sbfList.value?.size
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}