package id.trian.omkoro.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class BeritaGETLatLong(var id : String = "", var category: String = "", var title : String = "", var body : String="", var author : String="", var image : String="", var uid_author : String = "", var government : Boolean = false, var latitude : Double = 0.0, var longitude : Double = 0.0, var publish_date: Timestamp = Timestamp(0,0)) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(category)
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeString(author)
        parcel.writeString(image)
        parcel.writeString(uid_author)
        parcel.writeByte(if (government) 1 else 0)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeParcelable(publish_date, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeritaGETLatLong> {
        override fun createFromParcel(parcel: Parcel): BeritaGETLatLong {
            return BeritaGETLatLong(parcel)
        }

        override fun newArray(size: Int): Array<BeritaGETLatLong?> {
            return arrayOfNulls(size)
        }
    }
}