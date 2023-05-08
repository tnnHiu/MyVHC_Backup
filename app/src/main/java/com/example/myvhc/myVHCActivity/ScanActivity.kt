package com.example.myvhc.myVHCActivity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myvhc.myVHCActivity.AddMotorActivity.Companion.RESULT
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        setPermission()
    }

    override fun handleResult(p0: Result?) {
        startActivity(Intent(applicationContext, AddMotorActivity::class.java).apply {
            putExtra(RESULT, p0.toString())
        })
    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    private fun setPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
        }
    }
}