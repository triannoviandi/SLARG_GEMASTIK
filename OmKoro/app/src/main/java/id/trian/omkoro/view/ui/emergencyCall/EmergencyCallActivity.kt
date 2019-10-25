package id.trian.omkoro.view.ui.emergencyCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.R
import id.trian.omkoro.viewmodel.EmergencyCallViewModel
import kotlinx.android.synthetic.main.activity_emergency_call.*

class EmergencyCallActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var emergencyCallViewModel : EmergencyCallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_call)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Panggilan Darurat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        emergencyCallViewModel = ViewModelProviders.of(this)
            .get(EmergencyCallViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        emergency_card1.setOnClickListener {
            dialogSearchNip()
        }

    }

    fun dialogSearchNip(){
        MaterialDialog.Builder(this)
            .title("Panggilan Darurat")
            .content("Apakah anda yakin ingin memanggil bantuan pertolongan darurat?")
            .positiveText("Ya")
            .negativeText("Tidak")
            .onPositive { dialog, which ->
                callHospital()
            }
            .onNegative { dialog, which ->

            }
            .show()
    }

    fun callHospital(){
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it !=null){
                emergencyCallViewModel.callHospital(GeoPoint(it.latitude, it.longitude))
            }
            finish()
        }

    }

}
