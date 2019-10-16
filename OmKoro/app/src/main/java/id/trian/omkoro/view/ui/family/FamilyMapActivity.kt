package id.trian.omkoro.view.ui.family

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.trian.omkoro.R
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.*


class FamilyMapActivity : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_map)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Lokasi Keluarga"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.familyMap_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(mMap: GoogleMap?) {



        mMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-0.885818, 119.869112)))
        mMap?.moveCamera(CameraUpdateFactory.zoomTo(15.0f))

        addk1(mMap)
        addk2(mMap)
        addk3(mMap)
        addk4(mMap)
        addk5(mMap)


    }

    fun addk1(mMap: GoogleMap?){
        val bitmapdraw = resources.getDrawable(R.drawable.k2) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(-0.890828, 119.868232)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(smallMarker))))
    }

    fun addk2(mMap: GoogleMap?){
        val bitmapdraw = resources.getDrawable(R.drawable.k3) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(-0.897651, 119.867846)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(smallMarker))))
    }

    fun addk3(mMap: GoogleMap?){
        val bitmapdraw = resources.getDrawable(R.drawable.k4) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(-0.908603, 119.856130)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(smallMarker))))
    }

    fun addk4(mMap: GoogleMap?){
        val bitmapdraw = resources.getDrawable(R.drawable.k5) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(-0.906061, 119.871343)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(smallMarker))))
    }

    fun addk5(mMap: GoogleMap?){
        val bitmapdraw = resources.getDrawable(R.drawable.k6) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(-0.896020, 119.861645)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(smallMarker))))
    }


    fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val output: Bitmap

        if (bitmap.width > bitmap.height) {
            output = Bitmap.createBitmap(bitmap.height, bitmap.height, Bitmap.Config.ARGB_8888)
        } else {
            output = Bitmap.createBitmap(bitmap.width, bitmap.width, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        var r = 0f

        if (bitmap.width > bitmap.height) {
            r = (bitmap.height / 2).toFloat()
        } else {
            r = (bitmap.width / 2).toFloat()
        }

        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        canvas.drawCircle(r, r, r, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}
