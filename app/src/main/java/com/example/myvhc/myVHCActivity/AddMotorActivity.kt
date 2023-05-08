package com.example.myvhc.myVHCActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvhc.MainActivity
import com.example.myvhc.R
import com.example.myvhc.databinding.ActivityAddMotorBinding
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.VehicleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddMotorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMotorBinding
    private val auth = FirebaseAuth.getInstance().currentUser
    private val dbRef = FirebaseDatabase.getInstance().getReference("customer_vehicle")
    private lateinit var viewModel: VehicleViewModel

    companion object {
        const val RESULT = "RESULT"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            startActivity(Intent(applicationContext, ScanActivity::class.java))
        }

        val result = intent.getStringExtra(RESULT)
        if (result != null) {
            if (result.startsWith("http://") || result.startsWith("https://")) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result)))
            } else {
                binding.edtFrameNum.text = Editable.Factory.getInstance().newEditable(result)
            }
        }

        viewModel = ViewModelProvider(this)[VehicleViewModel::class.java]
        viewModel.vListSize.observe(this, Observer {
            val cvData = viewModel.cvList.value.orEmpty()
            val vData = viewModel.vList.value.orEmpty()
            addCustomerVehicle(cvData, vData)
        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setExpandableClickListener(binding.btnExpand1, binding.layoutExpandable1)
        setExpandableClickListener(binding.btnExpand2, binding.layoutExpandable2)
        setExpandableClickListener(binding.btnExpand3, binding.layoutExpandable3)
    }

    private fun setExpandableClickListener(button: View, layout: View) {
        button.setOnClickListener {
            if (layout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                layout.visibility = View.VISIBLE
                button.setBackgroundResource(R.drawable.ic_expand)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                layout.visibility = View.GONE
                button.setBackgroundResource(R.drawable.ic_collapse)
            }
        }
    }


    private fun addCustomerVehicle(cvData: List<CustomerVehicle>, vData: List<Vehicle>) {
        binding.btnAddVeicle.setOnClickListener {
            val chassisNum = binding.edtFrameNum.text.toString()
            if (chassisNum.isEmpty()) {
                binding.edtFrameNum.error = "Please enter chassis number"
                return@setOnClickListener
            }

            val hasRegisteredChassisNum = cvData.any { it.vehicleId.toString() == chassisNum }
            if (hasRegisteredChassisNum) {
                binding.edtFrameNum.error = "Registered chassis number"
                return@setOnClickListener
            }

            val isInvalidChassisNum = vData.none { it.vehicleChassisNum.toString() == chassisNum }
            if (isInvalidChassisNum) {
                binding.edtFrameNum.error = "Invalid chassis number"
                return@setOnClickListener
            }
            val customerVehicle = CustomerVehicle(auth?.uid, chassisNum, null, null)
            dbRef.child(dbRef.push().key!!).setValue(customerVehicle).addOnCompleteListener {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()
                binding.edtFrameNum.setText("")
            }
        }
    }
}



