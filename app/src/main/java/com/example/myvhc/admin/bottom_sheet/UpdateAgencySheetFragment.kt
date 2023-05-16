package com.example.myvhc.admin.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myvhc.R
import com.example.myvhc.admin.activity.AdminAgencyDetailActivity
import com.example.myvhc.databinding.FragmentUpdateAgencySheetBinding
import com.example.myvhc.models.Agency
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class UpdateAgencySheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateAgencySheetBinding
    private lateinit var agencyId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateAgencySheetBinding.inflate(layoutInflater, container, false)

        val agencyData = arguments?.getParcelable<Agency>("agencyData")
        agencyId = agencyData?.agencyId.toString()
        with(binding) {
            agencyAddress.text = agencyData?.agencyAddress?.toEditable()
            agencyLatitude.text = agencyData?.agencyLatitude?.toString()?.toEditable()
            agencyLongitude.text = agencyData?.agencyLongitude?.toString()?.toEditable()
            agencyName.text = agencyData?.agencyName?.toEditable()
            agencyPhoneNum.text = agencyData?.agencyPhoneNum?.toEditable()
            agencyWorkTime.text = agencyData?.agencyWorkTime?.toEditable()
            saveButton.setOnClickListener {
                val agencyAddress = binding.agencyAddress.text.toString()
                val agencyLatitude = binding.agencyLatitude.text.toString()
                val agencyLongitude = binding.agencyLongitude.text.toString()
                val agencyName = binding.agencyName.text.toString()
                val agencyPhoneNum = binding.agencyPhoneNum.text.toString()
                val agencyWorkTime = binding.agencyWorkTime.text.toString()
                updateAgency(
                    agencyAddress,
                    agencyLatitude,
                    agencyLongitude,
                    agencyName,
                    agencyPhoneNum,
                    agencyWorkTime
                )
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                val pushIntent = Intent(context, AdminAgencyDetailActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtras(Bundle().apply {
                        val agencyDataUpdate = Agency(
                            agencyId,
                            agencyName,
                            "1",
                            agencyPhoneNum,
                            agencyAddress,
                            agencyLatitude.toDouble(),
                            agencyLongitude.toDouble(),
                            agencyWorkTime
                        )
                        putParcelable("agencyDataUpdate", agencyDataUpdate)
                    })
                }
                startActivity(pushIntent)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.remove(this@UpdateAgencySheetFragment)?.commit()


            }

        }

        return binding.root
    }

    private fun updateAgency(
        agencyAddress: String,
        agencyLatitude: String,
        agencyLongitude: String,
        agencyName: String,
        agencyPhoneNum: String,
        agencyWorkTime: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("agencies").child(agencyId)
        val agency = Agency(
            agencyId,
            agencyName,
            "1",
            agencyPhoneNum,
            agencyAddress,
            agencyLatitude.toDouble(),
            agencyLongitude.toDouble(),
            agencyWorkTime
        )
        dbRef.setValue(agency)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}