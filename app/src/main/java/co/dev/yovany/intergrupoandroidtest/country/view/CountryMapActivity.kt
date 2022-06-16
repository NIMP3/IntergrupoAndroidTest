package co.dev.yovany.intergrupoandroidtest.country.view

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.dev.yovany.intergrupoandroidtest.R
import co.dev.yovany.intergrupoandroidtest.databinding.ActivityCountryMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class CountryMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityCountryMapBinding
    private lateinit var map: GoogleMap
    private var countryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryName = intent.getStringExtra(ARG_COUNTRY_NAME)
        binding.apply { btnBack.setOnClickListener { finish() } }
        createFragment()
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        countryName?.let { searchByName(it) }
    }

    private fun searchByName(name: String) {
        var addressList = emptyList<Address>()
        val geocoder = Geocoder(this)

        try {
            addressList = geocoder.getFromLocationName(name,1)
        }
        catch (e: IOException) {e.printStackTrace()}

        if (addressList.isNotEmpty()) {
            val address = addressList.first()
            val latLng = LatLng(address.latitude, address.longitude)
            map.addMarker(MarkerOptions().position(latLng).title(name))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
        }
    }

    companion object {
        const val ARG_COUNTRY_NAME = "arg_country_name"
    }
}