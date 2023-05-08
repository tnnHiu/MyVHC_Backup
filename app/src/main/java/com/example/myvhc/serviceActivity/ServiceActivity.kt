package com.example.myvhc.serviceActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myvhc.databinding.ActivityServiceBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.btnAddPicture.setOnClickListener {
            binding.imgDetail.visibility = View.VISIBLE
            binding.btnAddPicture.visibility = View.GONE
        }

        binding.imgDetail.setOnClickListener {
            binding.btnAddPicture.visibility = View.VISIBLE
            binding.imgDetail.visibility = View.GONE
        }

    }

    private fun updateLabel(myCalendar: Calendar?) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.txtDate.text = sdf.format(myCalendar!!.time)
    }
}