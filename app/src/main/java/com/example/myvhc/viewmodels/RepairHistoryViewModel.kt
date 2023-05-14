package com.example.myvhc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvhc.models.Agency
import com.example.myvhc.models.ServiceBookingForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RepairHistoryViewModel : ViewModel() {
    private var auth = FirebaseAuth.getInstance().currentUser
    private val dbRef = FirebaseDatabase.getInstance()
    private val rHRef = dbRef.getReference("service_booking_form")
    private val agencyListRef = dbRef.getReference("agencies")
    val rHList = MutableLiveData<ArrayList<ServiceBookingForm>>(ArrayList())
    var agencyList = MutableLiveData<ArrayList<Agency>>(ArrayList())
    val rHListSize = MutableLiveData<Int>()

    init {
        getRepairHistory()
    }

    private fun getRepairHistory() {
        val query = rHRef.orderByChild("customerId").equalTo(auth?.uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    rHList.value?.clear()
                    for (data in snapshot.children) {
                        val dataHR = data.getValue(ServiceBookingForm::class.java)
                        if (dataHR != null) {
                            rHList.value?.add(dataHR)
                            dataHR.agencyId?.let { getAgencyList(it) }

                        }

                    }
                    rHListSize.value = rHList.value?.size
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getAgencyList(agencyId: String) {
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
                    rHListSize.value = rHList.value?.size
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}
