package id.trian.omkoro.service.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class User(var nama: String= "", var nip:String="", var nomorhp: String="", var provinsi: String= "" ,
                var kota: String="", var alamat : String= "", var fotoProfile: String = "", var uid: String="" ,var last_location: GeoPoint = GeoPoint(0.0, 0.0 ))
