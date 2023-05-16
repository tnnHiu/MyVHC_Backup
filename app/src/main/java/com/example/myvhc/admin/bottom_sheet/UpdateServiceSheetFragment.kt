package com.example.myvhc.admin.bottom_sheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.myvhc.R
import com.example.myvhc.admin.DashboardAdminActivity
import com.example.myvhc.databinding.FragmentUpdateServiceSheetBinding
import com.example.myvhc.models.ServiceBookingForm
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class UpdateServiceSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateServiceSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateServiceSheetBinding.inflate(layoutInflater, container, false)

        val sbfData = arguments?.getParcelable<ServiceBookingForm>("sbfData")

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.radioButtonYes.id) {
                sbfData?.status = "1"
            } else {
                sbfData?.status = "0"
            }
        }
        binding.saveButton.setOnClickListener {
            if (sbfData != null) {
                updateSBF(sbfData)
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, DashboardAdminActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.remove(this@UpdateServiceSheetFragment)?.commit()

            }
        }
        return binding.root
    }

    private fun updateSBF(sbfData: ServiceBookingForm) {

        val dbRef = sbfData.customerId?.let {
            FirebaseDatabase.getInstance().getReference("service_booking_form").child(
                it
            )
        }
        dbRef?.setValue(sbfData)
    }

}