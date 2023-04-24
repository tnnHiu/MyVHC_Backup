package com.example.myvhc.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myvhc.models.CustomerVehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerVehicleViewModel : ViewModel() {

    private var auth = FirebaseAuth.getInstance().currentUser
    private val dbRef = FirebaseDatabase.getInstance().getReference("customer_vehicle")
    val customerVehicleList = MutableLiveData<ArrayList<CustomerVehicle>>(ArrayList())
    var customerVehicleListSize = MutableLiveData<Int>()


    init {
        getCustomerVehicleList()
    }

    private fun getCustomerVehicleList() {
        val query = dbRef.orderByChild("customerId").equalTo(auth?.uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    customerVehicleList.value?.clear()
                    for (cusVhc in snapshot.children) {
                        val data = cusVhc.getValue(CustomerVehicle::class.java)
                        if (data != null) {
                            customerVehicleList.value?.add(data)
                            Log.d("test", data.toString())
                        }
                    }
                    customerVehicleListSize.value = customerVehicleList.value?.size
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}