package id.trian.omkoro.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository

class EmergencyCallViewModel : ViewModel(){


    var firebaseRepository = FirebaseLoginRepository()


    fun callHospital(location : GeoPoint){
        firebaseRepository.getProfile().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot != null){
                val myProfile = documentSnapshot.toObject(User::class.java)
                firebaseRepository.emergencyCall(location, myProfile!!).addOnSuccessListener {
                    Log.d("emergency", "requested")
                }
            }


        }
    }

}