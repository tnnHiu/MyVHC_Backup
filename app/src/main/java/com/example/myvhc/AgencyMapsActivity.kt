package com.example.myvhc

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvhc.databinding.ActivityAgencyMapsBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.viewmodels.AgencyViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.auth.FirebaseAuth

class AgencyMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityAgencyMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var agencyViewModel: AgencyViewModel
    private lateinit var agencyList: ArrayList<Agency>
    private val permissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgencyMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        agencyViewModel = ViewModelProvider(this)[AgencyViewModel::class.java]
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getCurrentLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions =
            MarkerOptions().position(latLng).title(getString(R.string.your_location))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
        getNearbyAgency(latLng, googleMap)

    }

    @SuppressLint("InflateParams")
    private fun getNearbyAgency(currentLatLng: LatLng, googleMap: GoogleMap) {
        agencyViewModel.agencyListSize.observe(this, Observer {
            agencyList = agencyViewModel.agencyList.value!!
            val agencyView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.title_maps, null
            )
            val agencyName = agencyView.findViewById<TextView>(R.id.agencyName)
            val agencyAddress = agencyView.findViewById<TextView>(R.id.agencyAddress)
            val agencyCardView = agencyView.findViewById<CardView>(R.id.agencyCardView)
            for (agency in agencyList) {
                val agencyLat = agency.agencyLatitude
                val agencyLng = agency.agencyLongitude
                if (agencyLat != null && agencyLng != null) {
                    val agencyLatLng = LatLng(agencyLat, agencyLng)
                    if (getDistance(currentLatLng, agencyLatLng) <= 100000) {
                        agencyName.text = agency.agencyName
                        agencyAddress.text = agency.agencyAddress
                        val bitmap = Bitmap.createScaledBitmap(
                            viewToBitMap(agencyCardView)!!,
                            agencyCardView.width,
                            agencyCardView.height,
                            false
                        )
                        val agencyTitleMap = BitmapDescriptorFactory.fromBitmap(bitmap)
                        googleMap.addMarker(
                            MarkerOptions().position(agencyLatLng).icon(agencyTitleMap)
                        )
                    }
                }
            }
        })
    }

    private fun viewToBitMap(view: View): Bitmap? {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        view.measure(160, 120)
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    // get current location --------------------------------------------------------------start
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.mapAgency) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }
    // get current location --------------------------------------------------------------end

    // Cấp quyền truy cập ------------------------------------------------------------------------
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }

    // Tính khoảng cách giữa vị trí hiện tại với các cửa hàng trong bán kinh cho trước
    private fun getDistance(latLng1: LatLng, latLng2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results
        )
        return results[0]
    }
}

