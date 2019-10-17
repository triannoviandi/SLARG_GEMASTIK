package id.trian.omkoro.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import id.trian.omkoro.service.model.Berita
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository

class InputBeritaViewModel : ViewModel() {


    var firebaseRepository = FirebaseLoginRepository()

    fun inputBerita(beritaa: Berita){
        var berita = beritaa

        firebaseRepository.getProfile().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot!= null){
                val doc = documentSnapshot.toObject(User::class.java)
                val nama = doc!!.nama
                berita.author = nama
                firebaseRepository.uploadImage(berita.image).addOnSuccessListener {
                    firebaseRepository.uploadBeritaKebencanaan(berita, it.storage.downloadUrl.toString()).addOnSuccessListener {
                    }
                }
            }
        }

    }


}