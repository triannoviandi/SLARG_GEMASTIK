package id.trian.omkoro.service.model

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import java.sql.Timestamp

data class Berita(var title : String = "", var body : String="", var author : String="", var image : Uri, var uid_author : String = "", var government : Boolean = true)