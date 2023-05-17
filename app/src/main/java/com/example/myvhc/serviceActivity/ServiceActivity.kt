package com.example.myvhc.serviceActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myvhc.MainActivity
import com.example.myvhc.databinding.ActivityServiceBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.ServiceBookingForm
import com.example.myvhc.models.Vehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private val auth = FirebaseAuth.getInstance().currentUser
    private val dbRef = FirebaseDatabase.getInstance().getReference("service_booking_form")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getIntent = intent
        val bundle = getIntent.extras
        val cvData = bundle?.getParcelable<CustomerVehicle>("cvData")
        Log.i("checkData", cvData.toString())
        val vData = bundle?.getParcelable<Vehicle>("vData")
        val agencyId = bundle?.getString("agencyId")
        val agencyName = bundle?.getString("agencyName")
        val agencyAddress = bundle?.getString("agencyAddress")
        if (cvData != null && vData != null) {
            Glide.with(this).load(vData.vehicleImg).into(binding.vehicleImg)
            binding.txtVehicleName.text = vData.vehicleModel
            binding.txtLicensePlate.text = cvData.licensePlate
            binding.txtAgencyName.text = agencyName
            binding.txtAgencyAddress.text = agencyAddress
        }

        binding.btnSubmit.setOnClickListener {
            val customerId = auth?.uid
            val vehicleId = vData?.vehicleChassisNum
            val creationDate = Date().toString()
            val serviceDate = binding.txtDate.text.toString()
            val serviceHours = binding.txtTime.text.toString()
            val describe = binding.edtDescribe.text.toString()
            val status = "0"

            // Kiểm tra ràng buộc về thời gian
            val currentTime = Calendar.getInstance()
            val selectedDateTime = Calendar.getInstance()
            selectedDateTime.time = SimpleDateFormat(
                "dd/MM/yyyy HH:mm", Locale.US
            ).parse("$serviceDate $serviceHours")!!

            if (selectedDateTime.before(currentTime)) {
                Toast.makeText(this, "Please select a future date and time", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val serviceBookingForm = ServiceBookingForm(
                agencyId,
                customerId,
                vehicleId,
                creationDate,
                serviceDate,
                serviceHours,
                describe,
                status
            )
            orderService(serviceBookingForm)
        }

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        binding.iconDate.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.iconTime.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.txtTime.text = formattedTime
            }, startHour, startMinute, false).show()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateLabel(myCalendar: Calendar?) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.txtDate.text = sdf.format(myCalendar!!.time)
    }

    private fun orderService(serviceBookingForm: ServiceBookingForm) {
        serviceBookingForm.customerId?.let {
            dbRef.child(it).setValue(serviceBookingForm).addOnCompleteListener {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()

            }
        }
    }

}