package id.trian.omkoro.view.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import android.os.Build
import android.util.Log
import android.view.MenuItem
import id.trian.omkoro.R
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import id.trian.omkoro.view.ui.emergencyCall.EmergencyCallActivity

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

}