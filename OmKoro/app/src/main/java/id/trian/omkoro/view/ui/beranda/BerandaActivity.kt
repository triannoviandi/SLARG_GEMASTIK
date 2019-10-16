package id.trian.omkoro.view.ui.beranda

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import id.trian.omkoro.R
import id.trian.omkoro.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_beranda.*
import kotlinx.android.synthetic.main.activity_profile_setting.*

class BerandaActivity : AppCompatActivity() {

    lateinit var viewModel : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)



        viewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)

        getProfile()
    }

    private fun getProfile(){
        viewModel.checkExist().observe(this, Observer {
            if (it){
                Log.d("wewe", "truee")
                viewModel.getProfile().observe(this, Observer {
                    beranda_imgPhoto.setImageBitmap(decodeBase64(it.fotoProfile))
                    beranda_tvNama.setText(it.nama)
                    beranda_tvLokasi.setText(it.kota + ", " + it.provinsi)
                })
            } else {
                Log.d("wewe", "Tidak ada data")
                Toast.makeText(this, "Mohon Setting Profile Anda", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun decodeBase64(input: String): Bitmap {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

}
