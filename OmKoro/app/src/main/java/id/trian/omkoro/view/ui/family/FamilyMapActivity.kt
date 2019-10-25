package id.trian.omkoro.view.ui.family

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.trian.omkoro.R
import android.graphics.drawable.BitmapDrawable
import android.graphics.*
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.scale
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import id.trian.omkoro.viewmodel.FamilyViewModel
import kotlinx.android.synthetic.main.activity_family_map.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FamilyMapActivity : AppCompatActivity(), OnMapReadyCallback {


    lateinit var familyViewModel : FamilyViewModel
    lateinit var dialog : AlertDialog
    var firebaseRepository = FirebaseLoginRepository()
    lateinit var mMap : GoogleMap


    var list = ArrayList<String>()
    private var dummy: ArrayList<User>  = ArrayList()
    private var dummyFamilylist: ArrayList<User>  = ArrayList()

    private var spinnerArray: ArrayList<User>  = ArrayList()

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



        familyViewModel = ViewModelProviders.of(this)
            .get(FamilyViewModel::class.java)

        getFamilyList()
    }

    override fun onMapReady(mMap: GoogleMap?) {

        this.mMap = mMap!!
    }

    private fun getFamilyList(){
        dialog()
        familyViewModel.getFamilyCollection()?.observe(this, Observer {
            Log.d("galihtes", it.toString())
            if (it.isNotEmpty() && it[0] != "thisisnull"){
                CoroutineScope(Dispatchers.Main).launch {
                    val user = firebaseRepository.getAllProfile().await()
                    val alldata = user.toObjects(User::class.java)
                    val selectdata = it

                    alldata.forEach {
                        for (thisdata in selectdata){
                            if (thisdata == it.uid){
                                dummyFamilylist.add(it)
                            }
                        }
                    }
                    showFamily(dummyFamilylist)
                    dialog.dismiss()
                }
            } else{
                dialog.dismiss()
            }

        })
    }

    fun showFamily(familyList: ArrayList<User>  = ArrayList()){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(familyList[0].last_location.latitude,familyList[0].last_location.longitude), 15.0f))
        familyList.forEach {
            Log.d("familybrp", "2")
            add(it)
            spinnerArray.add(it)
        }
    }

    fun add(
        user: User
    ){
        Log.d("familybrp", user.fotoProfile)
        val bitmapdraw = resources.getDrawable(R.drawable.k2) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false)

        mMap.addMarker(
            MarkerOptions().position(LatLng(user.last_location.latitude, user.last_location.longitude)).title("Posisi Anda").icon(
                BitmapDescriptorFactory.fromBitmap(getCircularBitmap(decodeBase64(user.fotoProfile)!!).scale(120, 120, true))))


        list.add(user.nama)
        familyMap_spinner.setItems(list)

        familyMap_spinner.setOnItemSelectedListener { view, position, id, item ->
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(spinnerArray[position].last_location.latitude,spinnerArray[position].last_location.longitude), 15.0f))
        }
    }

    fun decodeBase64(input: String): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
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

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(r, r, r, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun dialog(){
        val padding  = 30
        val ll : LinearLayout = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(padding, padding, padding, padding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0,0, padding,0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText= TextView(this)
        tvText.text = "Loading ..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)

        dialog = builder.create()
        dialog.show()
        val window = dialog.window
        if (window != null){
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = 700
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }
}
