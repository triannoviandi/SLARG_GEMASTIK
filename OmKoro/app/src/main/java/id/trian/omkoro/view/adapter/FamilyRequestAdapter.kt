package id.trian.omkoro.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.trian.omkoro.R
import id.trian.omkoro.service.model.User
import kotlinx.android.synthetic.main.recycleview_family.view.*
import android.widget.AdapterView.OnItemClickListener
import kotlin.math.log


class FamilyRequestAdapter(private val list: MutableList<User>) : RecyclerView.Adapter<FamilyRequestAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        Log.d("galiha", "oncreate")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_family, parent, false)
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
        fun bindItems(user: User) {
            Log.d("galih", "onbind2")
            itemView.recycleview_family_imgPhoto.setImageBitmap(decodeBase64(user.fotoProfile))
            itemView.recycleview_family_tvNama.text = user.nama
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }

        fun decodeBase64(input: String): Bitmap {
            val decodedByte = Base64.decode(input, 0)
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        }


    }
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
