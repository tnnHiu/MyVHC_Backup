package com.example.myvhc.admin.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myvhc.admin.activity.AdminVehicleDetailActivity
import com.example.myvhc.databinding.FragmentUpdateVehicleSheetBinding
import com.example.myvhc.models.Vehicle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class UpdateVehicleSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentUpdateVehicleSheetBinding
    private lateinit var vehicleChassisNum: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateVehicleSheetBinding.inflate(layoutInflater, container, false)
        val vData = arguments?.getParcelable<Vehicle>("vData")
        vehicleChassisNum = vData?.vehicleChassisNum.toString()

        with(binding) {
            vehicleBrand.text = vData?.vehicleBrand?.toEditable()
            vehicleImg.text = vData?.vehicleImg?.toEditable()
            vehicleCylinderCap.text =
                extractNumber(vData?.vehicleCylinderCap.toString()).toEditable()
            vehicleModel.text = vData?.vehicleModel?.toEditable()
            vehiclePrice.text = extractNumber(vData?.vehiclePrice.toString()).toEditable()

            saveButton.setOnClickListener {
                val vehicleBrand = vehicleBrand.text.toString()
                val vehicleImg = vehicleImg.text.toString()
                val vehicleCylinderCap = "${vehicleCylinderCap.text}cc"
                val vehicleModel = vehicleModel.text.toString()
                val vehiclePrice = "${vehiclePrice.text} VNĐ"

                updateVehicle(
                    vehicleBrand, vehicleImg, vehicleCylinderCap, vehicleModel, vehiclePrice
                )
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()

                val pushIntent = Intent(context, AdminVehicleDetailActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtras(Bundle().apply {
                        val vDataUpdate = Vehicle(
                            vehicleChassisNum,
                            vehicleBrand,
                            vehicleImg,
                            vehicleModel,
                            vehicleCylinderCap,
                            vehiclePrice
                        )
                        putParcelable("vDataUpdate", vDataUpdate)
                    })
                }
                startActivity(pushIntent)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.remove(this@UpdateVehicleSheetFragment)?.commit()
            }
        }

        return binding.root
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun extractNumber(input: String): String = input.replace(Regex("\\D+"), "")

    private fun updateVehicle(
        vehicleBrand: String,
        vehicleImg: String,
        vehicleCylinderCap: String,
        vehicleModel: String,
        vehiclePrice: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("vehicles").child(vehicleChassisNum)
        val vehicle = Vehicle(
            vehicleChassisNum,
            vehicleBrand,
            vehicleImg,
            vehicleModel,
            vehicleCylinderCap,
            vehiclePrice
        )
        dbRef.setValue(vehicle)
    }
}

