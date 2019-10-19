package id.trian.omkoro.view.ui.beranda

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.trian.omkoro.R
import kotlinx.android.synthetic.main.activity_berita_input_maps.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.BitmapDescriptor
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.core.content.res.ResourcesCompat


class BeritaInputMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.inputBeritaMap_LockButton -> endActivity()
        }
    }

    private fun endActivity() {
        var intent = Intent()

        val myLocResult = myLocation

        intent.putExtra("location", myLocResult)
        setResult(Activity.RESULT_OK, intent)

        finish()
    }


    private lateinit var mMap: GoogleMap
    lateinit var myLocation: LatLng

    val LOCATION_PERMISSION = 42
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_input_maps)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Lokasi Bantuan"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        inputBeritaMap_LockButton.setOnClickListener(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.InputBeritaMap_Map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        cameratoLocation()
    }

    override fun onCameraIdle() {
        inputBeritaMap_Marker.visibility = View.GONE
        val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
            .icon(getMarker())
        mMap.addMarker(markerOptions)
        myLocation = mMap.cameraPosition.target
        inputBeritaMap_LockButton.background.setTint(Color.parseColor("#1AA8EB"))
    }

    override fun onCameraMove() {
        mMap.clear()
        inputBeritaMap_Marker.visibility = View.VISIBLE
        inputBeritaMap_LockButton.background.setTint(Color.parseColor("#c5c5c5"))
    }


    fun cameratoLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    myLocation = LatLng(location!!.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(LatLng(location!!.latitude, location.longitude)).title("Lokasi Bantuan").icon(
                        getMarker()))

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.0f))

                    mMap.setOnCameraIdleListener(this)
                    mMap.setOnCameraMoveListener(this)
                }



        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION)
        }
    }

    private fun getMarker(): BitmapDescriptor? {
        var vectorDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_marker_logistic_svg, null)
        var bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}
