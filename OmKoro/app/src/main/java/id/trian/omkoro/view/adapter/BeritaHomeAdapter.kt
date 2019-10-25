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
import id.trian.omkoro.service.model.BeritaGET
import id.trian.omkoro.service.model.BeritaHome
import id.trian.omkoro.service.model.User
import kotlinx.android.synthetic.main.activity_berita_detail.*
import kotlinx.android.synthetic.main.recycleview_beritaterbaru.view.*
import kotlinx.android.synthetic.main.recycleview_family.view.*


class BeritaHomeAdapter(private val list: MutableList<BeritaGET>) : RecyclerView.Adapter<BeritaHomeAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
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
        holder.bindItems(list[position])
    }

    //the class is hodling the list view
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(berita: BeritaGET) {
           //itemView.recycleview_berita_image.setImageBitmap(berita. )
            itemView.recycleview_berita_judul.text = berita.title
            Glide.with(itemView.context).load(berita.image).centerCrop().placeholder(R.drawable.berita_background).into(itemView.recycleview_berita_image)

            itemView.setOnClickListener {
                Log.d("galihberita", "siniadap")
                onItemClickCallback?.onItemClicked(berita)
            }
        }

        fun decodeBase64(input: String): Bitmap {
            val decodedByte = Base64.decode(input, 0)
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: BeritaGET)
    }
}