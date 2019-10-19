package id.trian.omkoro.service.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class BeritaBantuan(var title : String = "", var body : String="", var author : String="", var image : Uri, var uid_author : String = "", var government : Boolean = false, var location: LatLng = LatLng(0.0, 0.0))