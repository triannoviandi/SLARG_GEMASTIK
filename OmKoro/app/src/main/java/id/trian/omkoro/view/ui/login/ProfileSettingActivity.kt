package id.trian.omkoro.view.ui.login

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaredrummler.materialspinner.MaterialSpinner
import com.google.android.material.snackbar.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.activity_profile_setting.*
import android.provider.MediaStore
import com.bumptech.glide.Glide
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import id.trian.omkoro.R
import id.trian.omkoro.service.model.User
import id.trian.omkoro.viewmodel.ProfileViewModel
import java.io.ByteArrayOutputStream
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.Toolbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import id.trian.omkoro.view.ui.beranda.BerandaActivity
import id.trian.omkoro.view.ui.beranda.HomeActivity


class ProfileSettingActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var provinsiArray: Array<String>
    lateinit var sultengArray: Array<String>
    lateinit var jabarArray: Array<String>
    var uid = FirebaseAuth.getInstance().uid
    lateinit var firestoreViewModel : ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile_setting)

        //checkUser Exist
        //View Model Provider
        firestoreViewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)
        getProfile()

        //Declare Click Listener
        profile_btnAmbilFoto.setOnClickListener(this)
        profile_btnBukaGallery.setOnClickListener(this)
        profile_btnSelesai.setOnClickListener(this)


        //Setting Spinner Provinsi / Kab
        provinsiArray = resources.getStringArray(R.array.provinsi)
        sultengArray = resources.getStringArray(R.array.sulteng)
        jabarArray = resources.getStringArray(R.array.jabar)

        val listProv = provinsiArray.toList()
        val listSulteng = sultengArray.toList()
        val listJabar = jabarArray.toList()


        val spinner = findViewById<MaterialSpinner>(R.id.profile_spinnerProvinsi)
        val spinner2 = findViewById<MaterialSpinner>(R.id.profile_spinnerKota)

        spinner.setItems(listProv)
        spinner2.setItems(listSulteng)
        spinner.setOnItemSelectedListener { view, position, id, item ->

            if (position == 0){
                spinner2.setItems(listSulteng)
            } else if (position == 1){
                spinner2.setItems(listJabar)
            }


        }
        //End Setting spinner


        //Action Bar Back
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Pengaturan Profil"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    //onclickListener
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.profile_btnAmbilFoto -> dispatchTakePictureIntent()
            R.id.profile_btnBukaGallery -> takeFromGallery()
            R.id.profile_btnSelesai -> saveProfile()
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
            Glide.with(this).load(imageBitmap).centerCrop().into(profile_imgPhoto)
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            val selectedImage = data?.data

            profile_imgPhoto.setImageURI(selectedImage)
        }
    }


    //Save Profile Information
    private fun saveProfile(){
        if (!(profile_edtNama.text.toString().equals("") || profile_edtAlamat.text.toString().equals("")
            || profile_edtNip.text.toString().equals("")  || profile_edtAlamat.text.toString().equals("")) ){

            val imageView = findViewById<ImageView>(R.id.profile_imgPhoto)
            val bitmapPhoto : Bitmap = imageView.drawable.toBitmap()
            val encode : String = encodeTobase64(bitmapPhoto)

            val setUser = User(
                profile_edtNama.text.toString(),
                profile_edtNip.text.toString(),
                profile_edtNoHp.text.toString(),
                profile_spinnerProvinsi.text.toString(),
                profile_spinnerKota.text.toString(),
                profile_edtAlamat.text.toString(),
                encode,
                uid!!
                )


            firestoreViewModel.saveProfile(setUser)

            FirebaseMessaging.getInstance().subscribeToTopic("palu")
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d("firebase", "gagal")
                    }
                    Log.d("firebase", "sukses")
                }

            val intent : Intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        } else {
            Snackbar.make(
                profile_rootLayout,
                "Informasi Tidak Lengkap !",
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun getProfile(){
            firestoreViewModel.checkExist().observe(this, Observer {

                if (it){
                    firestoreViewModel.getProfile().observe(this, Observer {
                        Log.d("wewe", it.nama)
                        profile_imgPhoto.setImageBitmap(decodeBase64(it.fotoProfile))
                        profile_edtNama.setText(it.nama)
                        profile_edtNip.setText(it.nip)
                        profile_edtNoHp.setText(it.nomorhp)
                        profile_edtAlamat.setText(it.alamat)
                        profile_spinnerProvinsi.text = it.provinsi
                        profile_spinnerKota.text = it.kota
                    })
                } else {
                    Log.d("wewe", "falsee")
                }
            })
    }


    //ENCODE IMAGE FUNCTION
    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun decodeBase64(input: String): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
