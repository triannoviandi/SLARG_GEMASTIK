package id.trian.omkoro.view.ui.emergencyCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.trian.omkoro.R

class EmergencyCallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_call)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Panggilan Darurat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

    }
}
