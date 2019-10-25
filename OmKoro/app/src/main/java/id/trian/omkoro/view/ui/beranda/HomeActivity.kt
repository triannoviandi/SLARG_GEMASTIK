package id.trian.omkoro.view.ui.beranda

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import id.trian.omkoro.view.ui.emergencyCall.EmergencyCallActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.maps.android.PolyUtil
import id.trian.omkoro.R
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import id.trian.omkoro.utilities.MyWorker
import id.trian.omkoro.viewmodel.FamilyViewModel
import id.trian.omkoro.viewmodel.HomeViewModel
import id.trian.omkoro.viewmodel.ProfileViewModel
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var viewModel : HomeViewModel
    private val mutableList : MutableList<LatLng> = ArrayList()
    private val mutableList2 : MutableList<LatLng> = ArrayList()
    private val mutableList3 : MutableList<LatLng> = ArrayList()
    var firebaseRepository = FirebaseLoginRepository()

    private fun checking(){
        firebaseRepository.checkStateGempa().addOnSuccessListener {
            if (it != null){
                val state = it.getBoolean("state")
                if (state!!){
                    firebaseRepository.checkNotifExist().addOnSuccessListener { notified ->
                        if (notified.exists() && notified != null){
                            val state2 = notified.getBoolean("notified")
                            if (state2!! == false){
                                checkPolygonPosition()
                            }
                        }
                    }
                }
            }
        }
    }

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
                checkZonaMerah -> makeNotification(3)
                checkZonaKuning -> makeNotification(2)
                checkZonaHijau -> makeNotification(1)
            }
        }
    }

    private fun makeNotification(state: Int){

        var title : String = String()
        var body : String = String()

        when (state){
            1 -> {
                title = "Peringatan"
                body = ""
            }
            2 -> {
                title = "Peringatan Waspada Tsunami"
                body = "Anda berada di wilayah bahaya tsunami kuning(sedang), Segera mencari tempat yang lebih tinggi !"
            }
            3 -> {
                title = "Peringatan Waspada Tsunami"
                body = "Anda berada di wilayah bahaya tsunami merah(tinggi), Segera berpindah dari wilayah tersebut !"
            }
        }

        if (state != 1){
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var builder = NotificationCompat.Builder(this, "channel2")
                .setSmallIcon(R.drawable.ic_stat_app_icon)
                .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorPrimaryDark))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(body))
                .setAutoCancel(true)
                .setSound(uri)

            val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "channel2", "notif",
                    NotificationManager.IMPORTANCE_HIGH
                )
                val att = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
                channel.enableVibration(true)
                channel.enableLights(true)

                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
                channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
                mNotificationManager.createNotificationChannel(channel)
            }
            mNotificationManager.notify(2, builder.build())
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            val background = this.resources.getDrawable(R.color.colorPrimaryDark)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }

        viewModel = ViewModelProviders.of(this)
            .get(HomeViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

     //   startJob()

        checking()

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

//If the location permission has been granted, then start the TrackerService//

        bservice()

        val bundle=intent.extras
        if(bundle!=null)
        {
            if (bundle.getBoolean("fromNotifGempa", false)){
                safetyCheckDialog()
            }
        }

    }
    fun safetyCheckDialog(){
        val mDialog = BottomSheetMaterialDialog.Builder(this)
            .setTitle("Pemeriksaan Keselamatan")
            .setMessage("Apakah kondisi anda dalam keadaan selamat dan baik-baik saja?")
            .setCancelable(true)
            .setPositiveButton(
                "Dalam Keadaan Baik",
                AbstractDialog.OnClickListener { dialogInterface, which ->
                    dialogInterface.dismiss()
                    notifyFamilyUser()
                }
            )
            .setNegativeButton(
                "Membutuhkan Pertolongan",
                AbstractDialog.OnClickListener { dialogInterface, which ->
                    dialogInterface.dismiss()
                    startActivity(Intent(this, EmergencyCallActivity::class.java))
                }
            )
            .build()

        // Show Dialog
        mDialog.show()

    }

    private fun notifyFamilyUser() {
        firebaseRepository.getFamilyList().addOnSuccessListener {
            val myFamily = it.toObjects(User::class.java)
            myFamily.forEach {
                viewModel.sendNotifToFamily(it.uid)
            }
        }
    }


    fun bservice(){
    val periodicWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                            .addTag("MyWorker")
                            .build()
    WorkManager.getInstance().enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork)
}

}