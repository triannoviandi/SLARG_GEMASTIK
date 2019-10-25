package id.trian.omkoro.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.core.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.util.Log
import id.trian.omkoro.service.model.State_gempa
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository


class HomeViewModel : ViewModel() {

    var firebaseRepository = FirebaseLoginRepository()
    var state = State_gempa()
    var returnState = MutableLiveData<Boolean>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun state_gempa(): LiveData<Boolean>{
        firebaseRepository.checkGempaState().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            Log.d("berubah", "yeah")
           state = documentSnapshot!!.toObject(State_gempa::class.java)!!
            returnState.value = state.state
        }
        Log.d("berubah", returnState.value.toString())
        return returnState
    }

    fun sendNotifToFamily(family_uid : String){
        firebaseRepository.getProfile().addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot != null){
                val profile = documentSnapshot!!.toObject(User::class.java)
                firebaseRepository.sendNotifToFamily(family_uid, profile!!)
            }

        }

    }

}