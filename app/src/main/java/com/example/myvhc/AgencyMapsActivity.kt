package com.example.myvhc

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvhc.databinding.ActivityAgencyMapsBinding
import com.example.myvhc.databinding.ActivityMainBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.myVHCActivity.ListVehicleActivity
import com.example.myvhc.myVHCActivity.VehicleDetailActivity
import com.example.myvhc.serviceActivity.AgencyDetailActivity
import com.example.myvhc.serviceActivity.ServiceActivity
import com.example.myvhc.viewmodels.AgencyViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

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


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // --------------------
        getCurrentLocation()
        // --------------------
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val latLng = LatLng(15.975893731284755, 108.25232971660927)
        val markerOptions =
            MarkerOptions().position(latLng).title(getString(R.string.your_location))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
        getNearbyAgency(latLng, googleMap)

    }


    @SuppressLint("InflateParams")
    private fun createAgencyView(agencyName: String, agencyAddress: String): BitmapDescriptor {
        val marker = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.title_maps, null
        )
        val txtAgencyName = marker.findViewById<TextView>(R.id.agencyName)
        val txtAgencyAddress = marker.findViewById<TextView>(R.id.agencyAddress)
        txtAgencyName.text = agencyName
        val bitmap =
            Bitmap.createScaledBitmap(viewToBitmap(marker)!!, marker.width, marker.height, false)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun viewToBitmap(view: View): Bitmap? {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }


    private fun getNearbyAgency(currentLatLng: LatLng, googleMap: GoogleMap) {
        agencyViewModel = ViewModelProvider(this)[AgencyViewModel::class.java]
        agencyViewModel.agencyListSize.observe(this, Observer {
            agencyList = agencyViewModel.agencyList.value!!
            for (agency in agencyList) {
                val agencyLat = agency.agencyLatitude
                val agencyLng = agency.agencyLongitude
                if (agencyLat != null && agencyLng != null) {
                    val agencyLatLng = LatLng(agencyLat, agencyLng)
                    if (getDistance(currentLatLng, agencyLatLng) <= 10000) {
                        if (agency.agencyName != null && agency.agencyAddress != null) {
                            val icon = createAgencyView(agency.agencyName!!, agency.agencyAddress!!)
                            googleMap.addMarker(
                                MarkerOptions().position(agencyLatLng).icon(icon)
                            )
                            val getIntent = intent
                            val bundle = getIntent.extras
                            val cvData = bundle?.getParcelable<CustomerVehicle>("cvData")
                            val vData = bundle?.getParcelable<Vehicle>("vData")
                            googleMap.setOnMarkerClickListener {
                                val intent = Intent(this, AgencyDetailActivity::class.java)
                                val bundleAgencyMapsActivity = Bundle()
                                bundleAgencyMapsActivity.putParcelable("cvData", cvData)
                                bundleAgencyMapsActivity.putParcelable("vData", vData)
                                bundleAgencyMapsActivity.putString("agencyId", agency.agencyId)
                                bundleAgencyMapsActivity.putString("agencyName", agency.agencyName)
                                bundleAgencyMapsActivity.putString(
                                    "agencyAddress", agency.agencyAddress
                                )
                                bundleAgencyMapsActivity.putString(
                                    "agencyWorkTime", agency.agencyWorkTime
                                )
                                bundleAgencyMapsActivity.putString(
                                    "agencyPhoneNum", agency.agencyPhoneNum
                                )
                                intent.putExtras(bundleAgencyMapsActivity)
                                startActivity(intent)

                                return@setOnMarkerClickListener true
                            }
                        }
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
    private fun getDistance(latLng1: LatLng, latLng2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results
        )
        return results[0]
    }
}

