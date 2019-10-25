package id.trian.omkoro.view.ui.beranda


import android.content.Intent
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaDashboard
import id.trian.omkoro.service.model.BeritaGET
import id.trian.omkoro.view.adapter.BeritaDashboardAdapter
import id.trian.omkoro.viewmodel.GetBeritaViewModel
import kotlinx.android.synthetic.main.fragment_berita_masyarakat.*
import kotlinx.android.synthetic.main.fragment_berita_pemerintah.*
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class BeritaMasyarakatFragment : Fragment() {

    lateinit var recycleViewBerita : RecyclerView
    lateinit var getBeritaViewModel : GetBeritaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getBeritaViewModel = ViewModelProviders.of(this)
            .get(GetBeritaViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berita_masyarakat, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.d("galihpager","mas res")
    }

    override fun onPause() {
        super.onPause()
        Log.d("galihpager","mas pau")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewBerita = view.findViewById(R.id.beritamasyarakat_rv)
        recycleViewBerita.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val list = ArrayList<String>()
        list.add("Kebencanaan")
        list.add("Bantuan")
        masyarakat_spinner.setItems(list)


        callAdapter()
    }


    private fun callAdapter(){
        getBeritaViewModel.getBeritaKebencanaanMasyarakat()!!.observe(this, Observer {
            var listBeritaKebencanaanMasyarakat = ArrayList<BeritaGET>()
            it.forEach {thisBerita ->
                listBeritaKebencanaanMasyarakat.add(thisBerita)
            }
            val mutableList : MutableList<BeritaGET> = listBeritaKebencanaanMasyarakat
            var beritaAdapter = BeritaDashboardAdapter(mutableList)

            beritaAdapter.setOnItemClickCallback(object : BeritaDashboardAdapter.OnItemClickCallback{
                override fun onItemClicked(data: BeritaGET) {
                    startActivity(Intent(context, BeritaGET::class.java))
                    Log.d("galihberita", "sini")
                }

            })

            val adapter = beritaAdapter
            recycleViewBerita.adapter = adapter
        })
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
