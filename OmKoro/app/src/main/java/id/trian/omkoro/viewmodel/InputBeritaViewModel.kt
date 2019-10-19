package id.trian.omkoro.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import id.trian.omkoro.service.model.Berita
import id.trian.omkoro.service.model.BeritaBantuan
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import kotlinx.coroutines.tasks.await

class InputBeritaViewModel : ViewModel() {


    var firebaseRepository = FirebaseLoginRepository()


    fun inputBerita(beritaa: Berita){
        var berita = beritaa

        firebaseRepository.getProfile().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot!= null){
                val doc = documentSnapshot.toObject(User::class.java)
                val nama = doc!!.nama
                val uid = doc.uid
                berita.author = nama
                berita.uid_author = uid
                firebaseRepository.uploadImage(berita.image).addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {uri ->
                        firebaseRepository.uploadBeritaKebencanaan(berita, uri.toString()).addOnSuccessListener {
                        }
                    }

                }
            }
        }

    }

    fun inputBeritaBantuan(beritaa: BeritaBantuan){
        var berita = beritaa

        firebaseRepository.getProfile().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot!= null){
                val doc = documentSnapshot.toObject(User::class.java)
                val nama = doc!!.nama
                val uid = doc.uid
                berita.author = nama
                berita.uid_author = uid
                firebaseRepository.uploadImage(berita.image).addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {uri ->
                        firebaseRepository.uploadBeritaBantuan(berita, uri.toString()).addOnSuccessListener {
                        }
                    }

                }
            }
        }

    }


}