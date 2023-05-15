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
            if (!isInputValid()) return@setOnClickListener

            val chassisNumber = dbRef.push().key!!
            val vehicle = createVehicleFromInput(chassisNumber)
            saveVehicleToDatabase(chassisNumber, vehicle)
            clearInputFields()
            Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInputValid(): Boolean {
        with(binding) {
            val vehicleBrand = vehicleBrand.text.toString()
            val vehicleCylinderCap = vehicleCylinderCap.text.toString()
            val vehicleImg = vehicleImg.text.toString()
            val vehicleModel = vehicleModel.text.toString()
            val vehiclePrice = vehiclePrice.text.toString()

            if (vehicleBrand.isEmpty()) {
                binding.vehicleBrand.error = "Please enter brand"
                return false
            }
            if (vehicleCylinderCap.isEmpty()) {
                binding.vehicleCylinderCap.error = "Please enter cylinder capacity"
                return false
            }
            if (vehicleImg.isEmpty()) {
                binding.vehicleImg.error = "Please enter Image link"
                return false
            }
            if (vehicleModel.isEmpty()) {
                binding.vehicleModel.error = "Please enter model"
                return false
            }
            if (vehiclePrice.isEmpty()) {
                binding.vehiclePrice.error = "Please enter price"
                return false
            }
        }
        return true
    }

    private fun createVehicleFromInput(chassisNumber: String): Vehicle {
        with(binding) {
            val vehicleBrand = vehicleBrand.text.toString()
            val vehicleCylinderCap = vehicleCylinderCap.text.toString()
            val vehicleImg = vehicleImg.text.toString()
            val vehicleModel = vehicleModel.text.toString()
            val vehiclePrice = vehiclePrice.text.toString()

            return Vehicle(
                chassisNumber,
                vehicleBrand,
                vehicleImg,
                vehicleModel,
                vehicleCylinderCap,
                vehiclePrice
            )
        }
    }

    private fun saveVehicleToDatabase(chassisNumber: String, vehicle: Vehicle) {
        dbRef.child(chassisNumber).setValue(vehicle)
    }

    private fun clearInputFields() {
        with(binding) {
            vehicleBrand.setText("")
            vehicleCylinderCap.setText("")
            vehicleImg.setText("")
            vehicleModel.setText("")
            vehiclePrice.setText("")
        }
    }
}
