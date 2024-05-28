package com.adso.splashscreen

import  android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adso.splashscreen.databinding.ActivityUbiMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbiMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityUbiMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private val PERMISSIONS_REQUEST_ACCES_FINE_LOCATION = 1
    private var locationPermissionGranted = false
    private val tiendaLatLng = LatLng(2.4417367,-76.6044781)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbiMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (locationPermissionGranted){

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
        }
        mMap.isMyLocationEnabled = true


        fusedLocationClient.lastLocation.addOnSuccessListener { location->
            if (location != null){
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Ubicación actual"))
                mMap.addMarker(MarkerOptions().position(tiendaLatLng).title("Tienda"))
            }
        }
    }

}



