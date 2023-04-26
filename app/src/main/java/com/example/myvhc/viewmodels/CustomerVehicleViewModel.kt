package com.example.myvhc.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CustomerVehicleViewModel : ViewModel() {
    private var auth = FirebaseAuth.getInstance().currentUser
    private val dbRef = FirebaseDatabase.getInstance()
    private val customerVehicleRef = dbRef.getReference("customer_vehicle")
    private val vehicleRef = dbRef.getReference("vehicles")
    val cvList = MutableLiveData<ArrayList<CustomerVehicle>>(ArrayList())
    val vList = MutableLiveData<ArrayList<Vehicle>>(ArrayList())
    val vListSize = MutableLiveData<Int>()

    init {
        getData()
    }

    private fun getData() {
        val queryCV = customerVehicleRef.orderByChild("customerId").equalTo(auth?.uid)
        queryCV.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot1: DataSnapshot) {
                if (snapshot1.exists()) {
                    for (data in snapshot1.children) {
                        val cvData = data.getValue(CustomerVehicle::class.java)
                        if (cvData != null) {
                            cvList.value?.add(cvData)
                            cvData.vehicleId?.let { getVehicleList(it) }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getVehicleList(vehicleId: String) {
        val query = vehicleRef.orderByChild("vehicleChassisNum").equalTo(vehicleId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot2: DataSnapshot) {
                if (snapshot2.exists()) {
                    for (data in snapshot2.children) {
                        val vData = data.getValue(Vehicle::class.java)
                        if (vData != null) {
                            vList.value?.add(vData)
                        }
                    }
                    vListSize.value = cvList.value?.size
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}




