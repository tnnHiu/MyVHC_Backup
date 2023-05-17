package com.example.myvhc.myVHCActivity

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvhc.R
import com.example.myvhc.databinding.ActivityAgencyMapsBinding
import com.example.myvhc.models.Agency
import com.example.myvhc.models.CustomerVehicle
import com.example.myvhc.models.Vehicle
import com.example.myvhc.serviceActivity.AgencyDetailActivity
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

        // Khởi tạo và gán layout binding
        binding = ActivityAgencyMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo fusedLocationProviderClient để lấy vị trí hiện tại
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Lấy vị trí hiện tại và cập nhật lên bản đồ
        getCurrentLocation()

        // Đăng ký sự kiện click nút Back
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Thiết lập vị trí hiện tại trên bản đồ và đánh dấu
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
//        val latLng = LatLng(15.975893731284755, 108.25232971660927)
        val markerOptions =
            MarkerOptions().position(latLng).title(getString(R.string.your_location))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)

        // Lấy danh sách các cửa hàng gần và đánh dấu trên bản đồ
        getNearbyAgency(latLng, googleMap)
    }

    // Tạo view chứa thông tin cửa hàng

    @SuppressLint("InflateParams")
    private fun createAgencyView(agencyName: String): BitmapDescriptor {
        val marker = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.title_maps, null
        )
        val txtAgencyName = marker.findViewById<TextView>(R.id.agencyName)
        txtAgencyName.text = agencyName
        val bitmap =
            Bitmap.createScaledBitmap(viewToBitmap(marker)!!, marker.width, marker.height, false)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    // Chuyển view thành hình ảnh bitmap
    private fun viewToBitmap(view: View): Bitmap? {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    // Lấy danh sách cửa hàng gần và đánh dấu trên bản đồ
    private fun getNearbyAgency(currentLatLng: LatLng, googleMap: GoogleMap) {
        // Khởi tạo ViewModel và quan sát danh sách cửa hàng
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
                            // Tạo marker tương ứng với cửa hàng và thêm vào bản đồ
                            val icon = createAgencyView(agency.agencyName!!)
                            googleMap.addMarker(MarkerOptions().position(agencyLatLng).icon(icon))

                            // Đăng ký sự kiện click marker để chuyển tới trang chi tiết cửa hàng
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

    // Lấy vị trí hiện tại của thiết bị
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

    // Xử lý kết quả yêu cầu cấp quyền truy cập vị trí
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

    // Tính khoảng cách giữa hai điểm trên bản đồ
    private fun getDistance(latLng1: LatLng, latLng2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results
        )
        return results[0]
    }
}


