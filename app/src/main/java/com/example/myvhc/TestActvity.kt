package com.example.myvhc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myvhc.databinding.ActivityTestActvityBinding

class TestActvity : AppCompatActivity() {
    private lateinit var binding: ActivityTestActvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnback.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            Toast.makeText(this, "Quay lại rồi con chó",Toast.LENGTH_LONG).show()
        }

    }
}