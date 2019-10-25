package id.trian.omkoro.service.repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import id.trian.omkoro.service.model.Berita
import id.trian.omkoro.service.model.BeritaBantuan
import id.trian.omkoro.service.model.BeritaDashboard
import id.trian.omkoro.service.model.User
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FirebaseLoginRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var myAccount = FirebaseAuth.getInstance()
    var uid = FirebaseAuth.getInstance().uid!!
    var firebaseStorage = FirebaseStorage.getInstance()
    var storageReference = firebaseStorage.reference


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

    fun alsoSetUrFamily(user: User, uid: String): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(uid).collection("family_list").document(user.uid)

        val data = hashMapOf(
            "family_status" to true,
            "uid" to user.uid,
            "fotoProfile" to user.fotoProfile,
            "nama" to user.nama,
            "nip" to user.nip
        )
        return documentReference.set(data)
    }

    fun acceptFamilyRequest(user: User): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(this.uid).collection("family_list").document(user.uid)

        val data = hashMapOf(
            "family_status" to true,
            "uid" to user.uid,
            "fotoProfile" to user.fotoProfile,
            "nama" to user.nama,
            "nip" to user.nip
        )
        return documentReference.set(data)
    }

    fun deleteFamilyRequest(uid: String): Task<Void> {
        var documentReference = firestoreDB.collection("user").document(this.uid).collection("family_request").document(uid)
        return documentReference.delete()
    }

    fun saveUserLocation(geoPoint: GeoPoint){

        val data = hashMapOf(
            "last_location" to geoPoint,
            "last_location_time" to FieldValue.serverTimestamp()
        )
        firestoreDB.collection("user").document(uid).update(data).addOnSuccessListener {
            Log.d("galihloc", "bwer")
        }

        val data2 = hashMapOf(
            "tes" to "tes"
        )
        firestoreDB.collection("user").document(uid).collection("tesgenerateloc").add(data2)
    }


    //BACKEND BERITA

    fun uploadImage(uri: Uri): UploadTask {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
        val format = simpleDateFormat.format(Date())
        val wew = storageReference.child("berita").child(uid + format)
        
        return wew.putFile(uri)
    }

    fun uploadBeritaKebencanaan(berita: Berita, location: String): Task<DocumentReference> {
        val data = hashMapOf(
            "author" to berita.author,
            "body" to berita.body,
            "image" to location,
            "request_date" to FieldValue.serverTimestamp(),
            "title" to berita.title,
            "uid_author" to berita.uid_author,
            "government" to berita.government
        )
        val wew = firestoreDB.collection("request_berita_kebencanaan")

        return wew.add(data)
    }

    fun uploadBeritaBantuan(berita: BeritaBantuan, location: String): Task<DocumentReference> {
        val data = hashMapOf(
            "author" to berita.author,
            "body" to berita.body,
            "image" to location,
            "request_date" to FieldValue.serverTimestamp(),
            "title" to berita.title,
            "uid_author" to berita.uid_author,
            "government" to berita.government,
            "location" to berita.location
        )
        val wew = firestoreDB.collection("request_berita_bantuan")
        return wew.add(data)
    }

    fun getBeritaKebencanaanPemerintah(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("berita_kebencanaan").whereEqualTo("government",true)
        return documentReference.get()
    }

    fun getBeritaBantuanPemerintah(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("berita_bantuan").whereEqualTo("government",true)
        return documentReference.get()
    }

    fun getBeritaKebencanaanMasyarakat(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("berita_kebencanaan").whereEqualTo("government",false)
        return documentReference.get()
    }

    fun getBeritaBantuanMasyarakat(): Task<QuerySnapshot> {
        var documentReference = firestoreDB.collection("berita_bantuan").whereEqualTo("government",false)
        return documentReference.get()
    }

    fun getAnotherProfile(uid: String): DocumentReference {
        var documentReference = firestoreDB.collection("user").document(uid)
        return documentReference
    }

    fun checkStateGempa(): Task<DocumentSnapshot> {
        var documentReference = firestoreDB.collection("state").document("sedang_gempa")
        return documentReference.get()
    }

    fun checkNotifExist(): Task<DocumentSnapshot> {
        var documentReference = firestoreDB.collection("state").document("sedang_gempa").collection("notify_user").document(uid)
        return documentReference.get()
    }

    fun emergencyCall(location: GeoPoint, user: User): Task<DocumentReference> {

        val data = hashMapOf(
            "requester_name" to user.nama,
            "requester_uid" to user.uid,
            "requester_location" to location
        )

        var documentReference = firestoreDB.collection("hospital_call_request")
        return documentReference.add(data)
    }

    fun sendNotifToFamily(family_uid : String, profile: User): Task<DocumentReference> {
        val data = hashMapOf(
            "from" to uid,
            "to" to family_uid,
            "body" to "Saya dalam keadaan baik-baik saja",
            "nama" to profile.nama
        )

        var documentReference = firestoreDB.collection("messages").document(family_uid).collection("to")
        return documentReference.add(data)
    }


}