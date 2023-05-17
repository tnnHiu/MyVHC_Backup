package com.example.myvhc.admin.bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myvhc.R
import com.example.myvhc.databinding.FragmentAddAgencySheetBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.Vehicle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class AddAgencySheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddAgencySheetBinding
    private var dbRef = FirebaseDatabase.getInstance().getReference("agencies")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddAgencySheetBinding.inflate(layoutInflater, container, false)
        addAgency()
        return binding.root
    }

    private fun addAgency() {
        binding.saveButton.setOnClickListener {
            if (!isInputValid()) return@setOnClickListener
            val vehicleId = dbRef.push().key!!
            val vehicle = createAgencyFormInput(vehicleId)
            saveAgencyToDatabase(vehicleId, vehicle)
            clearInputFields()
            Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInputValid(): Boolean {
        with(binding) {
            val agencyAddress = agencyAddress.text.toString()
            val agencyLat = agencyLatitude.text.toString()
            val agencyLng = agencyLongitude.text.toString()
            val agencyName = agencyName.text.toString()
            val agencyPhoneNum = agencyPhoneNum.text.toString()
            val agencyWorkTime = agencyWorkTime.text.toString()
            if (agencyAddress.isEmpty()) {
                binding.agencyAddress.error = "Please enter address"
                return false
            }
            if (agencyLat.isEmpty()) {
                binding.agencyAddress.error = "Please enter Latitude"
                return false
            }
            if (agencyLng.isEmpty()) {
                binding.agencyAddress.error = "Please enter Longitude"
                return false
            }
            if (agencyName.isEmpty()) {
                binding.agencyAddress.error = "Please enter name"
                return false
            }
            if (agencyPhoneNum.isEmpty()) {
                binding.agencyAddress.error = "Please enter phone number"
                return false
            }
            if (agencyWorkTime.isEmpty()) {
                binding.agencyAddress.error = "Please enter work time"
                return false
            }
        }
        return true
    }

    private fun createAgencyFormInput(agencyId: String): Agency = with(binding) {
        val agencyAddress = agencyAddress.text.toString()
        val agencyLat = agencyLatitude.text.toString()
        val agencyLng = agencyLongitude.text.toString()
        val agencyName = agencyName.text.toString()
        val agencyPhoneNum = agencyPhoneNum.text.toString()
        val agencyWorkTime = agencyWorkTime.text.toString()
        Agency(
            agencyId,
            agencyName,
            "1",
            agencyPhoneNum,
            agencyAddress,
            agencyLat.toDouble(),
            agencyLng.toDouble(),
            agencyWorkTime
        )
    }

    private fun saveAgencyToDatabase(agencyId: String, agency: Agency) {
        dbRef.child(agencyId).setValue(agency)
    }

    private fun clearInputFields() {
        with(binding) {
            agencyAddress.setText("")
            agencyLatitude.setText("")
            agencyLongitude.setText("")
            agencyName.setText("")
            agencyPhoneNum.setText("")
            agencyWorkTime.setText("")
        }
    }
}

