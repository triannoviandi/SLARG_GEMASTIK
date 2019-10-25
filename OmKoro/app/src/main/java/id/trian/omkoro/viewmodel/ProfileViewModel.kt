package id.trian.omkoro.viewmodel

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.iid.FirebaseInstanceId
import id.trian.omkoro.service.model.RequestFamily
import id.trian.omkoro.service.model.User
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

class ProfileViewModel : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirebaseLoginRepository()
    var thisuser : MutableLiveData<User> = MutableLiveData()
    var condition: MutableLiveData<Boolean> = MutableLiveData()

    // save address to firebase
    fun saveProfile(user: User){
        firebaseRepository.saveProfile(user).addOnSuccessListener {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
                Log.d("wew", it.token)
            }


        }
    }

    // get realtime updates from firebase regarding saved addresses
    fun getProfile(): LiveData<User>{
        firebaseRepository.getProfile().addSnapshotListener(EventListener<DocumentSnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                thisuser.value = null
                return@EventListener
            }
            thisuser.value = value!!.toObject(User::class.java)
        })
        return thisuser
    }

    fun checkExist(): LiveData<Boolean>{
        firebaseRepository.getProfile().addSnapshotListener(EventListener<DocumentSnapshot> { value, _ ->
            condition.value = value?.toObject(User::class.java) != null
        })
        return condition
    }
}