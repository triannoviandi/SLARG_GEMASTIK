package id.trian.omkoro.service.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class BeritaBantuan(var title : String = "", var body : String="", var author : String="", var image : Uri, var uid_author : String = "", var government : Boolean = false, var location: GeoPoint = GeoPoint(0.0, 0.0))