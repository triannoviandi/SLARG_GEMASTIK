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
import id.trian.omkoro.service.model.BeritaDashboard
import id.trian.omkoro.service.model.BeritaHome
import kotlinx.android.synthetic.main.recycleview_beritadashboard.view.*
import kotlinx.android.synthetic.main.recycleview_beritaterbaru.view.*

class BeritaDashboardAdapter(private val list: MutableList<BeritaDashboard>) : RecyclerView.Adapter<BeritaDashboardAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    public fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
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
        Log.d("galihbro", "pos = $position")
        Log.d("galihbro", "size = " + list.size.toString())
        if (position == list.size-1){
            Log.d("galihbro", "lastt")
            holder.lastItem()
        }
    }

    //the class is hodling the list view
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(berita: BeritaDashboard) {
            itemView.beritadashboard_img.setImageBitmap(decodeBase64(berita.gambar))
            itemView.beritadashboard_judul.text = berita.judul
            itemView.beritadashboard_waktu.text = berita.waktu_pembuatan
            itemView.beritadashboard_pembuat.text = berita.penulis
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(berita)
            }
        }

        fun lastItem(){
            itemView.pemerintah_viewEnd.requestLayout()
            itemView.pemerintah_viewEnd.layoutParams.height = 200
        }

        fun decodeBase64(input: String): Bitmap {
            val decodedByte = Base64.decode(input, 0)
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: BeritaDashboard)
    }
}