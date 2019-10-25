package id.trian.omkoro.view.ui.evakuasi

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import id.trian.omkoro.R
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import androidx.core.content.FileProvider
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.common.util.Hex
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_evakuasi.*


class EvakuasiActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val LOCATION_PERMISSION = 42
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val mutableList : MutableList<LatLng> = ArrayList()
    private val mutableList2 : MutableList<LatLng> = ArrayList()
    private val mutableList3 : MutableList<LatLng> = ArrayList()


    private fun initList() {
        mutableList.clear()
        mutableList2.clear()
        mutableList3.clear()

        mutableList.add(LatLng(-0.807410, 119.810811))
        mutableList.add(LatLng(-0.827042, 119.811498))
        mutableList.add(LatLng(-0.842833, 119.819308))
        mutableList.add(LatLng(-0.848873, 119.824866))
        mutableList.add(LatLng(-0.878084, 119.839650))
        mutableList.add(LatLng(-0.883105, 119.866387))
        mutableList.add(LatLng(-0.873750, 119.869004))
        mutableList.add(LatLng(-0.861435, 119.876558))
        mutableList.add(LatLng(-0.855869, 119.875793))
        mutableList.add(LatLng(-0.833513, 119.878325))
        mutableList.add(LatLng(-0.829136, 119.876608))
        mutableList.add(LatLng(-0.822742, 119.880299))
        mutableList.add(LatLng(-0.818880, 119.877381))
        mutableList.add(LatLng(-0.804204, 119.874377))
        mutableList.add(LatLng(-0.784851, 119.857726))
        mutableList.add(LatLng(-0.771849, 119.855365))
        mutableList.add(LatLng(-0.761894, 119.856653))
        mutableList.add(LatLng(-0.755114, 119.858584))
        mutableList.add(LatLng(-0.742970, 119.852018))
        mutableList.add(LatLng(-0.724217, 119.854636))
        mutableList.add(LatLng(-0.704467, 119.846353))
        mutableList.add(LatLng(-0.700165, 119.850805))
        mutableList.add(LatLng(-0.705057, 119.860419))
        mutableList.add(LatLng(-0.712867, 119.865676))
        mutableList.add(LatLng(-0.718231, 119.867564))
        mutableList.add(LatLng(-0.727178, 119.863229))
        mutableList.add(LatLng(-0.740545, 119.864538))
        mutableList.add(LatLng(-0.756808, 119.867671))
        mutableList.add(LatLng(-0.770616, 119.869924))
        mutableList.add(LatLng(-0.788510, 119.874044))
        mutableList.add(LatLng(-0.800181, 119.881512))
        mutableList.add(LatLng(-0.809708, 119.883014))
        mutableList.add(LatLng(-0.822195, 119.888807))
        mutableList.add(LatLng(-0.833352, 119.885717))
        mutableList.add(LatLng(-0.850387, 119.885846))
        mutableList.add(LatLng(-0.864204, 119.883571))
        mutableList.add(LatLng(-0.882141, 119.877477))
        mutableList.add(LatLng(-0.889307, 119.869109))
        mutableList.add(LatLng(-0.891195, 119.855676))
        mutableList.add(LatLng(-0.885831, 119.838296))
        mutableList.add(LatLng(-0.879910, 119.831343))
        mutableList.add(LatLng(-0.859785, 119.820658))
        mutableList.add(LatLng(-0.837857, 119.809371))
        mutableList.add(LatLng(-0.815801, 119.804908))
        mutableList.add(LatLng(-0.810534, 119.804962))

        mutableList2.add(LatLng( -0.700165, 119.850805))
        mutableList2.add(LatLng( -0.705057, 119.860419))
        mutableList2.add(LatLng( -0.712867, 119.865676))
        mutableList2.add(LatLng( -0.718231, 119.867564))
        mutableList2.add(LatLng( -0.727178, 119.863229))
        mutableList2.add(LatLng( -0.740545, 119.864538))
        mutableList2.add(LatLng( -0.756808, 119.867671))
        mutableList2.add(LatLng( -0.770616, 119.869924))
        mutableList2.add(LatLng( -0.788510, 119.874044))
        mutableList2.add(LatLng( -0.800181, 119.881512))
        mutableList2.add(LatLng( -0.809708, 119.883014))
        mutableList2.add(LatLng( -0.822195, 119.888807))
        mutableList2.add(LatLng( -0.833352, 119.885717))
        mutableList2.add(LatLng( -0.850387, 119.885846))
        mutableList2.add(LatLng( -0.864204, 119.883571))
        mutableList2.add(LatLng( -0.882141, 119.877477))
        mutableList2.add(LatLng( -0.889307, 119.869109))
        mutableList2.add(LatLng( -0.891195, 119.855676))
        mutableList2.add(LatLng( -0.885831, 119.838296))
        mutableList2.add(LatLng( -0.879910, 119.831343))
        mutableList2.add(LatLng( -0.859785, 119.820658))
        mutableList2.add(LatLng( -0.837857, 119.809371))
        mutableList2.add(LatLng( -0.815801, 119.804908))
        mutableList2.add(LatLng( -0.810534, 119.804962))
        mutableList2.add(LatLng( -0.813602, 119.797602))
        mutableList2.add(LatLng( -0.819202, 119.795467))
        mutableList2.add(LatLng( -0.839069, 119.806109))
        mutableList2.add(LatLng( -0.862027, 119.815283))
        mutableList2.add(LatLng( -0.887709, 119.825463))
        mutableList2.add(LatLng( -0.896097, 119.836461))
        mutableList2.add(LatLng( -0.898779, 119.866116))
        mutableList2.add(LatLng( -0.887365, 119.888689))
        mutableList2.add(LatLng( -0.849733, 119.895234))
        mutableList2.add(LatLng( -0.814717, 119.899353))
        mutableList2.add(LatLng( -0.775239, 119.886994))
        mutableList2.add(LatLng( -0.718456, 119.872531))
        mutableList2.add(LatLng( -0.702665, 119.864377))
        mutableList2.add(LatLng( -0.696314, 119.856738))


        mutableList3.add(LatLng( -0.700165, 119.850805))
        mutableList3.add(LatLng( -0.813602, 119.797602))
        mutableList3.add(LatLng( -0.819202, 119.795467))
        mutableList3.add(LatLng( -0.839069, 119.806109))
        mutableList3.add(LatLng( -0.862027, 119.815283))
        mutableList3.add(LatLng( -0.887709, 119.825463))
        mutableList3.add(LatLng( -0.896097, 119.836461))
        mutableList3.add(LatLng( -0.898779, 119.866116))
        mutableList3.add(LatLng( -0.887365, 119.888689))
        mutableList3.add(LatLng( -0.849733, 119.895234))
        mutableList3.add(LatLng( -0.814717, 119.899353))
        mutableList3.add(LatLng( -0.775239, 119.886994))
        mutableList3.add(LatLng( -0.718456, 119.872531))
        mutableList3.add(LatLng( -0.702665, 119.864377))
        mutableList3.add(LatLng( -0.696314, 119.856738))
        mutableList3.add(LatLng( -0.693729, 119.875664))
        mutableList3.add(LatLng( -0.701957, 119.887895))
        mutableList3.add(LatLng( -0.716129, 119.898236))
        mutableList3.add(LatLng( -0.776301, 119.920886))
        mutableList3.add(LatLng( -0.814867, 119.930810))
        mutableList3.add(LatLng( -0.849551, 119.922496))
        mutableList3.add(LatLng( -0.956762, 119.966248))
        mutableList3.add(LatLng( -0.964191, 119.956508))
        mutableList3.add(LatLng( -0.933296, 119.884411))
        mutableList3.add(LatLng( -0.943852, 119.878617))
        mutableList3.add(LatLng( -0.937330, 119.829093))
        mutableList3.add(LatLng( -0.930893, 119.820424))
        mutableList3.add(LatLng( -0.852797, 119.765975))
        mutableList3.add(LatLng( -0.836630, 119.761975))
    }

    private fun checkPolygonPosition(){
        fusedLocationClient.lastLocation.addOnSuccessListener {
            initList()
            val myLoc = LatLng(it.latitude, it.longitude)
            val checkZonaMerah = PolyUtil.containsLocation(myLoc, mutableList, false)
            val checkZonaKuning = PolyUtil.containsLocation(myLoc, mutableList2, false)
            val checkZonaHijau = PolyUtil.containsLocation(myLoc, mutableList3, false)

            when {
                checkZonaMerah -> {
                    changeStatus(3)
                }
                checkZonaKuning -> {
                    changeStatus(2)
                }
                checkZonaHijau -> {
                    changeStatus(1)
                } else -> {
                changeStatus(1)
            }
            }
        }
    }

    private fun changeStatus(i: Int) {

        if (i == 1){
            evakuasi_img.setImageResource(R.drawable.statusgreen)
            evakuasi_title.text = "Anda berada diwilayah aman Tsunami"
            evakuasi_body.text = "Mohon untuk tidak mendekat wilayah bahaya tinggi tsunami"
        } else if (i ==2 ){
            evakuasi_img.setImageResource(R.drawable.statusyellow)
            evakuasi_title.text = "Anda berada diwilayah bahaya tsunami sedang"
            evakuasi_body.text = "Mohon untuk segera mencari tempat lebih tinggi!"
        } else if (i==3){
            evakuasi_img.setImageResource(R.drawable.statusred)
            evakuasi_title.text = "Anda berada diwilayah bahaya tsunami tinggi"
            evakuasi_body.text = "Mohon segera mungkin berpindah ke wilayah aman!"
        } else {
            evakuasi_img.setImageResource(R.drawable.statusgreen)
            evakuasi_title.text = "Anda berada diwilayah aman Tsunami"
            evakuasi_body.text = "Mohon untuk tidak mendekat wilayah bahaya tinggi tsunami"
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evakuasi)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Zona Evakuasi"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPolygonPosition()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = false

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    updateMapLocation(location)
                }

            addPolygon(mMap)
            addPolygon2(mMap)
            addPolygon3(mMap)



        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION)
        }


    }


    private fun addPolygon(googleMap: GoogleMap){
        val polygon1 = googleMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(-0.807410, 119.810811),
                    LatLng(-0.827042, 119.811498),
                    LatLng(-0.842833, 119.819308),
                    LatLng( -0.848873, 119.824866),
                    LatLng( -0.878084, 119.839650),
                    LatLng( -0.883105, 119.866387),
                    LatLng( -0.873750, 119.869004),
                    LatLng( -0.861435, 119.876558),
                    LatLng( -0.855869, 119.875793),
                    LatLng( -0.833513, 119.878325),
                    LatLng( -0.829136, 119.876608),
                    LatLng( -0.822742, 119.880299),
                    LatLng( -0.818880, 119.877381),
                    LatLng( -0.804204, 119.874377),
                    LatLng( -0.784851, 119.857726),
                    LatLng( -0.771849, 119.855365),
                    LatLng( -0.761894, 119.856653),
                    LatLng( -0.755114, 119.858584),
                    LatLng( -0.742970, 119.852018),
                    LatLng( -0.724217, 119.854636),
                    LatLng( -0.704467, 119.846353),
                    LatLng( -0.700165, 119.850805),
                    LatLng( -0.705057, 119.860419),
                    LatLng( -0.712867, 119.865676),
                    LatLng( -0.718231, 119.867564),
                    LatLng( -0.727178, 119.863229),
                    LatLng( -0.740545, 119.864538),
                    LatLng( -0.756808, 119.867671),
                    LatLng( -0.770616, 119.869924),
                    LatLng( -0.788510, 119.874044),
                    LatLng( -0.800181, 119.881512),
                    LatLng( -0.809708, 119.883014),
                    LatLng( -0.822195, 119.888807),
                    LatLng( -0.833352, 119.885717),
                    LatLng( -0.850387, 119.885846),
                    LatLng( -0.864204, 119.883571),
                    LatLng( -0.882141, 119.877477),
                    LatLng( -0.889307, 119.869109),
                    LatLng( -0.891195, 119.855676),
                    LatLng( -0.885831, 119.838296),
                    LatLng( -0.879910, 119.831343),
                    LatLng( -0.859785, 119.820658),
                    LatLng( -0.837857, 119.809371),
                    LatLng( -0.815801, 119.804908),
                    LatLng( -0.810534, 119.804962)
                ).fillColor(Color.parseColor("#59F44336")).strokeWidth(0.toFloat())
        )
        polygon1.tag = "alpha"
    }

    private fun addPolygon2(googleMap: GoogleMap){
        val polygon2 = googleMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng( -0.700165, 119.850805),
                    LatLng( -0.705057, 119.860419),
                    LatLng( -0.712867, 119.865676),
                    LatLng( -0.718231, 119.867564),
                    LatLng( -0.727178, 119.863229),
                    LatLng( -0.740545, 119.864538),
                    LatLng( -0.756808, 119.867671),
                    LatLng( -0.770616, 119.869924),
                    LatLng( -0.788510, 119.874044),
                    LatLng( -0.800181, 119.881512),
                    LatLng( -0.809708, 119.883014),
                    LatLng( -0.822195, 119.888807),
                    LatLng( -0.833352, 119.885717),
                    LatLng( -0.850387, 119.885846),
                    LatLng( -0.864204, 119.883571),
                    LatLng( -0.882141, 119.877477),
                    LatLng( -0.889307, 119.869109),
                    LatLng( -0.891195, 119.855676),
                    LatLng( -0.885831, 119.838296),
                    LatLng( -0.879910, 119.831343),
                    LatLng( -0.859785, 119.820658),
                    LatLng( -0.837857, 119.809371),
                    LatLng( -0.815801, 119.804908),
                    LatLng( -0.810534, 119.804962),
                    LatLng( -0.813602, 119.797602),
                    LatLng( -0.819202, 119.795467),
                    LatLng( -0.839069, 119.806109),
                    LatLng( -0.862027, 119.815283),
                    LatLng( -0.887709, 119.825463),
                    LatLng( -0.896097, 119.836461),
                    LatLng( -0.898779, 119.866116),
                    LatLng( -0.887365, 119.888689),
                    LatLng( -0.849733, 119.895234),
                    LatLng( -0.814717, 119.899353),
                    LatLng( -0.775239, 119.886994),
                    LatLng( -0.718456, 119.872531),
                    LatLng( -0.702665, 119.864377),
                    LatLng( -0.696314, 119.856738)

                ).fillColor(Color.parseColor("#59FFEB3B")).strokeWidth(0.toFloat())
        )
        polygon2.tag = "alpha"
    }

    private fun addPolygon3(googleMap: GoogleMap){
        val polygon2 = googleMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng( -0.700165, 119.850805),
                    LatLng( -0.813602, 119.797602),
                    LatLng( -0.819202, 119.795467),
                    LatLng( -0.839069, 119.806109),
                    LatLng( -0.862027, 119.815283),
                    LatLng( -0.887709, 119.825463),
                    LatLng( -0.896097, 119.836461),
                    LatLng( -0.898779, 119.866116),
                    LatLng( -0.887365, 119.888689),
                    LatLng( -0.849733, 119.895234),
                    LatLng( -0.814717, 119.899353),
                    LatLng( -0.775239, 119.886994),
                    LatLng( -0.718456, 119.872531),
                    LatLng( -0.702665, 119.864377),
                    LatLng( -0.696314, 119.856738),
                    LatLng( -0.693729, 119.875664),
                    LatLng( -0.701957, 119.887895),
                    LatLng( -0.716129, 119.898236),
                    LatLng( -0.776301, 119.920886),
                    LatLng( -0.814867, 119.930810),
                    LatLng( -0.849551, 119.922496),
                    LatLng( -0.956762, 119.966248),
                    LatLng( -0.964191, 119.956508),
                    LatLng( -0.933296, 119.884411),
                    LatLng( -0.943852, 119.878617),
                    LatLng( -0.937330, 119.829093),
                    LatLng( -0.930893, 119.820424),
                    LatLng( -0.852797, 119.765975),
                    LatLng( -0.836630, 119.761975)

                ).fillColor(Color.parseColor("#594CAF50")).strokeWidth(0.toFloat())
        )
        polygon2.tag = "alpha"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode,
            permissions,
            grantResults)
        if (requestCode == LOCATION_PERMISSION) {
            if (permissions.size == 1 &&
                permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
            }
        }
    }


    private fun updateMapLocation(location: Location?) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location!!.latitude, location.longitude)))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f))
        mMap.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)).title("Posisi Anda").icon(getMarker()))
       // Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
    }

    private fun initLocationTracking() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    updateMapLocation(location)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest(),
            locationCallback,
            null)
    }

    override fun onResume() {
        super.onResume()
        if( ::mMap.isInitialized ) {
            initLocationTracking()
        }
    }

    private fun getMarker(): BitmapDescriptor? {
        var vectorDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_marker_evakuasi, null)
        var bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}
