package id.trian.omkoro.view.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaHome
import id.trian.omkoro.service.model.User
import kotlinx.android.synthetic.main.recycleview_beritaterbaru.view.*
import kotlinx.android.synthetic.main.recycleview_family.view.*


class BeritaHomeAdapter(private val list: MutableList<BeritaHome>) : RecyclerView.Adapter<BeritaHomeAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    public fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_beritaterbaru, parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d("galih", "onbind")
        holder.bindItems(list[position])
    }

    //the class is hodling the list view
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(berita: BeritaHome) {
           itemView.recycleview_berita_image.setImageBitmap(berita.gambar)
            itemView.recycleview_berita_judul.text = berita.judul
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(berita)
            }
        }

        fun decodeBase64(input: String): Bitmap {
            val decodedByte = Base64.decode(input, 0)
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: BeritaHome)
    }
}