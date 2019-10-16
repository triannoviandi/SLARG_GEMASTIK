package id.trian.omkoro.view.ui.beranda

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.view.View
import com.bumptech.glide.Glide
import id.trian.omkoro.R
import kotlinx.android.synthetic.main.activity_berita_input.*
import kotlinx.android.synthetic.main.activity_profile_setting.*

class BeritaInput : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.inputBerita_btnUploadCamera -> dispatchTakePictureIntent()
            R.id.inputBerita_btnUploadGallery -> takeFromGallery()
        }
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

        inputBerita_btnUploadGallery.setOnClickListener(this)
        inputBerita_btnUploadCamera.setOnClickListener(this)

        val list = ArrayList<String>()
        list.add("Kebencanaan")
        list.add("Bantuan")
        inputBerita_SpinnerJenis.setItems(list)

        inputBerita_SpinnerJenis.setOnItemSelectedListener { view, position, id, item ->

            if (position == 0){
                inputBerita_llLokasi.visibility = View.GONE
            } else {
                inputBerita_llLokasi.visibility = View.VISIBLE
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
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            val selectedImage = data?.data
            inputBerita_Preview.setImageURI(selectedImage)
            inputBerita_berhasil.visibility = View.VISIBLE
        }
    }
}
