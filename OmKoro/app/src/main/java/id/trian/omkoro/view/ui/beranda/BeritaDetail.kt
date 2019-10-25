package id.trian.omkoro.view.ui.beranda

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaGETLatLong
import id.trian.omkoro.viewmodel.GetBeritaViewModel
import kotlinx.android.synthetic.main.activity_berita_detail.*
import java.util.*

class BeritaDetail : AppCompatActivity() {

    lateinit var firestoreViewModel : GetBeritaViewModel
    lateinit var geoPoint : GeoPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_detail)



        firestoreViewModel = ViewModelProviders.of(this)
            .get(GetBeritaViewModel::class.java)

        val berita = intent.getParcelableExtra("detail_berita") as BeritaGETLatLong
        val jenisBeritaBantuan = intent.getBooleanExtra("bantuan", false)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Berita"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        BeritaDetail_btnLokasiBantuan.setOnClickListener {
            val ft = supportFragmentManager!!.beginTransaction()
            val prev = supportFragmentManager!!.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            val dialogFragment = BeritaBantuanMapsFragment.newInstance(geoPoint.latitude, geoPoint.longitude)
            dialogFragment.show(ft, "dialog")
        }
        initBerita(berita, jenisBeritaBantuan)
    }

    fun initBerita(beritaGET: BeritaGETLatLong, jenisBeritaBantuan : Boolean){
        beritaDetail_judul.text = beritaGET.title
        beritaDetail_body.text = beritaGET.body
        when (beritaGET.government){
            true -> beritaDetail_government.text = "Berita Pemerintah"
            false -> beritaDetail_government.text = "Berita Masyarakat"
        }
        when (jenisBeritaBantuan){
            true -> {
                BeritaDetail_btnLokasiBantuan.visibility = View.VISIBLE
                geoPoint = GeoPoint(beritaGET.latitude, beritaGET.longitude)
            }
            false -> {
                BeritaDetail_btnLokasiBantuan.visibility = View.GONE
            }
        }

        beritaDetail_authorName.text = beritaGET.author
        beritaDetail_date.text = getDate(beritaGET.publish_date.seconds)
        Glide.with(applicationContext).load(beritaGET.image).centerCrop().placeholder(R.drawable.berita_background).into(beritaDetail_image)


        firestoreViewModel.getImageAuthor(beritaGET.uid_author).observe(this, androidx.lifecycle.Observer {
            if (it !=null){
                beritaDetail_authorImg.setImageBitmap(decodeBase64(it))
            }
        })


    }



    private fun getDate(milliSeconds: Long): String {
        var calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis=milliSeconds * 1000L
        var date = android.text.format.DateFormat.format("E dd-MM-yyyy", calendar).toString()

        var datereplaced : String
        if (date.startsWith("Sun", 0,true)) {
            datereplaced = date.replace("Sun", "Minggu", true)
        }
        else if (date.startsWith("Mon", 0,true)) {
            datereplaced = date.replace("Mon", "Senin", true)
        }
        else if (date.startsWith("Tue", 0,true)) {
            datereplaced = date.replace("Tue", "Selasa", true)
        }
        else if (date.startsWith("Wed", 0,true)) {
            datereplaced = date.replace("Wed", "Rabu", true)
        }
        else if (date.startsWith("Thu", 0,true)) {
            datereplaced = date.replace("Thu", "Kamis", true)
        }
        else if (date.startsWith("Fri", 0,true)) {
            datereplaced = date.replace("Fri", "Jumat", true)
        }
        else if (date.startsWith("Sat", 0,true)) {
            datereplaced = date.replace("Sat", "Sabtu", true)
        } else {
            datereplaced = ""
        }

        return datereplaced
    }

    fun decodeBase64(input: String): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

}
