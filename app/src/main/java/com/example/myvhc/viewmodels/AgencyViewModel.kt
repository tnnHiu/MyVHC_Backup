package com.example.myvhc.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvhc.models.Agency
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AgencyViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("agencies")
    var agencyList = MutableLiveData<ArrayList<Agency>>(ArrayList())
    var agencyListSize = MutableLiveData<Int>()

    init {
        getAgencyList()
    }

    private fun getAgencyList() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    agencyList.value?.clear()
                    for (agency in snapshot.children) {
                        val dataAgency = agency.getValue(Agency::class.java)
                        if (dataAgency != null) {
                            Log.d("agencyData", dataAgency.toString())
                            agencyList.value?.add(dataAgency)
                        }
                    }
                    agencyListSize.value = agencyList.value?.size
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}