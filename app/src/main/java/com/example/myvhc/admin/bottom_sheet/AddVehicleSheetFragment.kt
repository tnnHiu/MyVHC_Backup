package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAddVehicleSheetBinding
import com.example.myvhc.models.Vehicle
import com.example.myvhc.viewmodels.VehicleViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class AddVehicleSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddVehicleSheetBinding
    private var dbRef = FirebaseDatabase.getInstance().getReference("vehicles")
    private lateinit var vehicleVM: VehicleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddVehicleSheetBinding.inflate(layoutInflater, container, false)

        vehicleVM = ViewModelProvider(this)[VehicleViewModel::class.java]
        vehicleVM.vListSize.observe(viewLifecycleOwner, Observer {
            val vData = vehicleVM.vList.value.orEmpty()
            addVehicle(vData)
        })



        return binding.root
    }

    private fun addVehicle(vData: List<Vehicle>) {
        binding.saveButton.setOnClickListener {
            val vehicleBrand = binding.vehicleBrand.text.toString()
            val vehicleCylinderCap = binding.vehicleCylinderCap.toString()
            val vehicleImg = binding.vehicleImg.text.toString()
            val vehicleModel = binding.vehicleModel.text.toString()
            val vehiclePrice = binding.vehiclePrice.text.toString()
            if (vehicleBrand.isEmpty()) {
                binding.vehicleBrand.error = "Please enter brand"
                return@setOnClickListener
            }
            if (vehicleCylinderCap.isEmpty()) {
                binding.vehicleCylinderCap.error = "Please enter cylinder capacity"
                return@setOnClickListener
            }
            if (vehicleImg.isEmpty()) {
                binding.vehicleImg.error = "Please enter Image link"
                return@setOnClickListener
            }
            if (vehicleModel.isEmpty()) {
                binding.vehicleModel.error = "Please enter model"
                return@setOnClickListener
            }
            if (vehiclePrice.isEmpty()) {
                binding.vehicleBrand.error = "Please enter price"
                return@setOnClickListener
            }
            val chassisNumber = dbRef.push().key!!
            val vehicle = Vehicle(
                chassisNumber,
                vehicleBrand,
                vehicleImg,
                vehicleModel,
                vehicleCylinderCap,
                vehiclePrice
            )
            dbRef.child(chassisNumber).setValue(vehicle).addOnCompleteListener {
                binding.vehicleBrand.setText("")
                binding.vehicleCylinderCap.setText("")
                binding.vehicleImg.setText("")
                binding.vehicleModel.setText("")
                binding.vehiclePrice.setText("")
                Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show()
            }


        }

    }
}