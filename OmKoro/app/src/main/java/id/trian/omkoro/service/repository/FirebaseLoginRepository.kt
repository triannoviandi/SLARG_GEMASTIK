package id.trian.omkoro.service.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.*
import id.trian.omkoro.service.model.User
import kotlinx.coroutines.tasks.await
import java.util.*
import java.util.concurrent.TimeUnit

class FirebaseLoginRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var myAccount = FirebaseAuth.getInstance()
    var uid = FirebaseAuth.getInstance().uid!!

    //checkGempaState
    fun checkGempaState(): DocumentReference{
        var documentReference = firestoreDB.collection("state").document("sedang_gempa")
        return documentReference
    }

    // save address to firebase
    fun saveProfile(user: User): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(uid)
        return documentReference.set(user)
    }

    // get saved addresses from firebase
    fun getProfile(): DocumentReference {
        var documentReference = firestoreDB.collection("user").document(uid)
        return documentReference
    }

    fun checkDocument(): Task<DocumentSnapshot>{
        var documentReference = firestoreDB.collection("user").document(uid)
        return documentReference.get()
    }


    //send request family
//    fun sendRequestFamily(): Task<Void>{
//        var documentReference = firestoreDB.collection("")
//    }

    fun checkUidByNip(nip: String): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("user").whereEqualTo("nip", nip).limit(1)
        return documentReference.get()
    }

    //add family
    fun addFamily(uid: String): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(uid).collection("family_request").document(this.uid)
        val myData = hashMapOf(
            "uid" to this.uid,
            "requested" to true
        )
        return documentReference.set(myData)
    }

    fun getFamilyList(): Task<QuerySnapshot> {
     var documentReference = firestoreDB.collection("user").document(uid).collection("family_list")
    return documentReference.get()
}

fun getRequestList(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("user").document(uid).collection("family_request")
        return documentReference.get()
    }

    fun getRequestList_profile(uid : String): Task<DocumentSnapshot> {
        var documentReference = firestoreDB.collection("user").document(uid)
        return documentReference.get()
    }


    fun getAllProfile(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("user")
        return documentReference.get()
    }

    fun addFamilyCollection(nip : String){
        var documentReference = firestoreDB.collection("user").whereEqualTo("nip", nip).addSnapshotListener { querySnapshot, firebaseFirestoreException ->

        }
    }

    fun acceptFamilyRequest(uid: String): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(this.uid).collection("family_list").document(uid)
        val data = hashMapOf(
            "family_status" to true
        )
        return documentReference.set(data)
    }

    fun deleteFamilyRequest(uid: String): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(this.uid).collection("family_request").document(uid)

        return documentReference.delete()
    }
}