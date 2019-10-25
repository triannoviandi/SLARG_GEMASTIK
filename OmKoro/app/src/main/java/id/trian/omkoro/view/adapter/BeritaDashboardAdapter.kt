package id.trian.omkoro.view.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaDashboard
import id.trian.omkoro.service.model.BeritaGET
import id.trian.omkoro.service.model.BeritaHome
import kotlinx.android.synthetic.main.recycleview_beritadashboard.view.*
import kotlinx.android.synthetic.main.recycleview_beritaterbaru.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class BeritaDashboardAdapter(private val list: MutableList<BeritaGET>) : RecyclerView.Adapter<BeritaDashboardAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_beritadashboard, parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bindItems(list[position])

    }

    //the class is hodling the list view
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(berita: BeritaGET) {
            Glide.with(itemView.context).load(berita.image).centerCrop().placeholder(R.drawable.berita_background).into(itemView.beritadashboard_img)

            //itemView.beritadashboard_img.setImageBitmap(berita.)
            itemView.beritadashboard_judul.text = berita.title
            itemView.beritadashboard_waktu.text = getDate(berita.publish_date.seconds)
            itemView.beritadashboard_pembuat.text = berita.author
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(berita)

            }
        }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: BeritaGET)
    }

    private fun getDate(milliSeconds: Long): String {
        var calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis=milliSeconds * 1000L
        var date = android.text.format.DateFormat.format("E dd-MM-yyyy", calendar).toString()

        var datereplaced : String
        if (date.startsWith("Sun", 0,true)) {
            datereplaced = date.replace("Sun", "Minggu", true)
        }
        else if (date.startsWith("Mon", 0,true)) {
            datereplaced = date.replace("Mon", "Senin", true)
        }
        else if (date.startsWith("Tue", 0,true)) {
            datereplaced = date.replace("Tue", "Selasa", true)
        }
        else if (date.startsWith("Wed", 0,true)) {
            datereplaced = date.replace("Wed", "Rabu", true)
        }
        else if (date.startsWith("Thu", 0,true)) {
            datereplaced = date.replace("Thu", "Kamis", true)
        }
        else if (date.startsWith("Fri", 0,true)) {
            datereplaced = date.replace("Fri", "Jumat", true)
        }
        else if (date.startsWith("Sat", 0,true)) {
            datereplaced = date.replace("Sat", "Sabtu", true)
        } else {
            datereplaced = ""
        }

        return datereplaced
    }
}