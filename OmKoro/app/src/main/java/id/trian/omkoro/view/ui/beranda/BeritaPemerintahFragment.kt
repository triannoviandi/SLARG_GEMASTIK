package id.trian.omkoro.view.ui.beranda


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.trian.omkoro.service.model.*
import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaHome
import id.trian.omkoro.view.adapter.BeritaHomeAdapter
import kotlinx.android.synthetic.main.fragment_berita_pemerintah.*
import java.io.ByteArrayOutputStream
import id.trian.omkoro.view.adapter.*
import kotlinx.android.synthetic.main.fragment_berita_masyarakat.*


class BeritaPemerintahFragment : Fragment() {

    lateinit var recycleViewBerita : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_berita_pemerintah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewBerita = view.findViewById(R.id.beritapemerintah_rv)
        recycleViewBerita.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val list = ArrayList<String>()
        list.add("Kebencanaan")
        list.add("Bantuan")
        pemerintah_spiner.setItems(list)

        callAdapter()
    }

    private fun callAdapter(){
        var listBeritaHome = ArrayList<BeritaDashboard>()
        val img1 : Bitmap = resources.getDrawable(R.drawable.b1).toBitmap()
        val img2 : Bitmap = resources.getDrawable(R.drawable.b2).toBitmap()
        val img3 : Bitmap = resources.getDrawable(R.drawable.b3).toBitmap()
        val img4 : Bitmap = resources.getDrawable(R.drawable.b4).toBitmap()
        val img5 : Bitmap = resources.getDrawable(R.drawable.b5).toBitmap()
        listBeritaHome.add(
            BeritaDashboard(
                "Bantuan BMKG tepat sasaran di Daerah Palu",
                "aaaaa",
                img1,
                "123",
                "Trian"
            )
        )
        listBeritaHome.add(
            BeritaDashboard(
                "Bantuan BMKG tepat sasaran di Daerah Palu",
                "aaaaa",
                img2,
                "123",
                "Trian"
            )
        )
        listBeritaHome.add(
            BeritaDashboard(
                "Bantuan BMKG tepat sasaran di Daerah Palu",
                "aaaaa",
                img3,
                "123",
                "Trian"
            )
        )
        listBeritaHome.add(
            BeritaDashboard(
                "Bantuan BMKG tepat sasaran di Daerah Palu",
                "aaaaa",
                img4,
                "123",
                "Trian"
            )
        )
        listBeritaHome.add(
            BeritaDashboard(
                "Bantuan BMKG tepat sasaran di Daerah Palu",
                "aaaaa",
                img5,
                "123",
                "Trian"
            )
        )
        val mutableList : MutableList<BeritaDashboard> = listBeritaHome
        var beritaAdapter = BeritaDashboardAdapter(mutableList)

        beritaAdapter.setOnItemClickCallback(object : BeritaDashboardAdapter.OnItemClickCallback{
            override fun onItemClicked(data: BeritaDashboard) {
            }

        })

        val adapter = beritaAdapter
        recycleViewBerita.adapter = adapter
    }


    fun decodeBase64(input: String): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}
