package com.example.myvhc.myVHCActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.myvhc.R
import com.example.myvhc.databinding.ActivityAddMotorBinding

class AddMotorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMotorBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnExpand1.setOnClickListener {
            if (binding.layoutExpandable1.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable1.visibility = View.VISIBLE
                binding.btnExpand1.setImageResource(R.drawable.ic_expand)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable1.visibility = View.GONE
                binding.btnExpand1.setImageResource(R.drawable.ic_collapse)            }
        }

        binding.btnExpand2.setOnClickListener {
            if (binding.layoutExpandable2.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable2.visibility = View.VISIBLE
                binding.btnExpand2.setImageResource(R.drawable.ic_expand)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable2.visibility = View.GONE
                binding.btnExpand2.setImageResource(R.drawable.ic_collapse)            }
        }

        binding.btnExpand3.setOnClickListener {
            if (binding.layoutExpandable3.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable3.visibility = View.VISIBLE
                binding.btnExpand3.setImageResource(R.drawable.ic_expand)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.layoutExpandable3.visibility = View.GONE
                binding.btnExpand3.setImageResource(R.drawable.ic_collapse)            }
        }

    }
}