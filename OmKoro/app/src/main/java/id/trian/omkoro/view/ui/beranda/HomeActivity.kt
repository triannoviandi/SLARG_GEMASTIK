package id.trian.omkoro.view.ui.beranda

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import android.os.Build
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import id.trian.omkoro.view.ui.emergencyCall.EmergencyCallActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import id.trian.omkoro.R
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import id.trian.omkoro.utilities.MyWorker
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.getWindow()
            val background = this.getResources().getDrawable(R.color.colorPrimaryDark)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent))
            window.setBackgroundDrawable(background)
        }

        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

     //   startJob()


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


fun bservice(){
    val periodicWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                            .addTag("MyWorker")
                            .build();
WorkManager.getInstance().enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
}

}