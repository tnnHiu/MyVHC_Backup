package com.example.myvhc

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AgencyMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
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

        agencyViewModel.agencyListSize.observe(this, Observer {
            agencyList = agencyViewModel.agencyList.value!!
            for (agency in agencyList) {
                val agencyLat = agency.agencyLatitude
                val agencyLng = agency.agencyLongitude
                if (agencyLat != null && agencyLng != null) {
                    val agencyLatLng = LatLng(agencyLat, agencyLng)
                    if (getDistance(latLng, agencyLatLng) <= 10000000) {
                        googleMap.addMarker(
                            MarkerOptions().position(agencyLatLng).title("${agency.agencyName}")
                        )
                    }
                }
            }
        })

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
    fun getDistance(latLng1: LatLng, latLng2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results
        )
        return results[0]
    }
}

