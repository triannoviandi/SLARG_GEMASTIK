package id.trian.omkoro.view.ui.beranda

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import id.trian.omkoro.R
import id.trian.omkoro.service.model.Berita
import id.trian.omkoro.viewmodel.InputBeritaViewModel
import kotlinx.android.synthetic.main.activity_berita_input.*
import kotlinx.android.synthetic.main.activity_profile_setting.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import android.content.ContextWrapper
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import java.io.*
import com.google.android.gms.maps.model.*
import id.trian.omkoro.service.model.BeritaBantuan
import java.util.*
import kotlin.collections.ArrayList


class BeritaInput : AppCompatActivity(), View.OnClickListener {

    lateinit var uriImage : Uri
    lateinit var firestoreViewModel : InputBeritaViewModel
    var kordinatBantuan: LatLng? = null
    var bantuan: Boolean = false

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.inputBerita_btnUploadCamera -> dispatchTakePictureIntent()
            R.id.inputBerita_btnUploadGallery -> takeFromGallery()
            R.id.inputBerita_btnKirim -> kirimBerita()
        }
    }

    private fun inputLokasi() {
        val intent = Intent(this, BeritaInputMapsActivity::class.java)
        startActivityForResult(intent, 10)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_input)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Buat Berita"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        firestoreViewModel = ViewModelProviders.of(this)
            .get(InputBeritaViewModel::class.java)

        inputBerita_btnUploadGallery.setOnClickListener(this)
        inputBerita_btnUploadCamera.setOnClickListener(this)
        inputBerita_btnKirim.setOnClickListener(this)

        val list = ArrayList<String>()
        list.add("Kebencanaan")
        list.add("Bantuan")
        inputBerita_SpinnerJenis.setItems(list)

        inputBerita_SpinnerJenis.setOnItemSelectedListener { view, position, id, item ->

            if (position == 0){
                inputBerita_llLokasi.visibility = View.GONE
                bantuan = false
            } else {
                inputBerita_llLokasi.visibility = View.VISIBLE
                bantuan = true
                inputLokasi()
            }

        }
    }

    //Function Take Picture Camera
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, 1)
            }
        }
    }

    //Function Take Picture from Gallery
    private fun takeFromGallery(){
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            inputBerita_berhasil.visibility = View.VISIBLE
            Glide.with(this).load(imageBitmap).centerCrop().into(inputBerita_Preview)
            uriImage = bitmapToFile(imageBitmap)
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            val selectedImage = data?.data
            inputBerita_Preview.setImageURI(selectedImage)
            inputBerita_berhasil.visibility = View.VISIBLE
            if (selectedImage != null){
                uriImage = selectedImage
            }

        } else if (requestCode == 10){
            var location : LatLng? = data!!.getParcelableExtra("location")
            kordinatBantuan = location
            inputberita_tvKordinat.text = kordinatBantuan.toString()
        }
    }

    fun formValidation(): Boolean{
        return !(inputBerita_tvJudul.text!!.isEmpty() || inputBerita_tvIsi.text!!.isEmpty() || (inputBerita_berhasil.visibility == View.GONE))
    }


    private fun kirimBerita(){
        if (formValidation()){
            if (bantuan && kordinatBantuan != null){
                val berita = BeritaBantuan(
                    inputBerita_tvJudul.text.toString(),
                    inputBerita_tvIsi.text.toString(),
                    "",
                    uriImage,
                    "",
                    false,
                    kordinatBantuan!!
                )

                firestoreViewModel.inputBeritaBantuan(berita)
                Toast.makeText(applicationContext, "Berhasil! Berita anda dalam proses validasi admin", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val berita = Berita(
                    inputBerita_tvJudul.text.toString(),
                    inputBerita_tvIsi.text.toString(),
                    "",
                    uriImage,
                    "",
                    false
                )

                firestoreViewModel.inputBerita(berita)
                Toast.makeText(applicationContext, "Berhasil! Berita anda dalam proses validasi admin", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else{
            Toast.makeText(applicationContext, "Mohon lengkapi form", Toast.LENGTH_SHORT).show()
        }


    }

    private fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }
}



