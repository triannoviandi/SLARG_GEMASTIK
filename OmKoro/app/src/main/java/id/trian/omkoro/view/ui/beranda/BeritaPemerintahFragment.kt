package id.trian.omkoro.view.ui.beranda

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.service.model.*
import id.trian.omkoro.R
import id.trian.omkoro.service.model.BeritaHome
import id.trian.omkoro.view.adapter.BeritaHomeAdapter
import kotlinx.android.synthetic.main.fragment_berita_pemerintah.*
import java.io.ByteArrayOutputStream
import id.trian.omkoro.view.adapter.*
import id.trian.omkoro.viewmodel.GetBeritaViewModel
import id.trian.omkoro.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_berita_masyarakat.*

class BeritaPemerintahFragment : Fragment() {

    lateinit var recycleViewBerita : RecyclerView
    lateinit var getBeritaViewModel : GetBeritaViewModel
    lateinit var dialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getBeritaViewModel = ViewModelProviders.of(this)
            .get(GetBeritaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_berita_pemerintah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleViewBerita = view.findViewById(R.id.beritapemerintah_rv)
        recycleViewBerita.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val list = ArrayList<String>()
        list.add("Kebencanaan")
        list.add("Bantuan")
        callAdapter()
        pemerintah_spiner.setItems(list)
        pemerintah_spiner.setOnItemSelectedListener { view, position, id, item ->
            when (position) {
                0 -> callAdapter()
                1 -> callAdapterBantuan()
            }
        }



    }

    private fun callAdapterBantuan() {
        dialog()
        getBeritaViewModel.getBeritaBantuanPemerintah()!!.observe(this, Observer {
            Log.d("galihberitata", "berubah")
            if (!it.isEmpty()){
                var listBeritaBantuanPemerintah = ArrayList<BeritaGET>()
                it.forEach {thisBerita ->
                    listBeritaBantuanPemerintah.add(thisBerita)
                }
                val mutableList : MutableList<BeritaGET> = listBeritaBantuanPemerintah
                var beritaAdapter = BeritaDashboardAdapter(mutableList)

                beritaAdapter.setOnItemClickCallback(object : BeritaDashboardAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: BeritaGET) {
                        var beritaPass = BeritaGETLatLong(
                            data.id,
                            data.category,
                            data.title,
                            data.body,
                            data.author,
                            data.image,
                            data.uid_author,
                            data.government,
                            data.location.latitude,
                            data.location.longitude,
                            data.publish_date
                        )

                        var intent = Intent(context, BeritaDetail::class.java)
                        intent.putExtra("detail_berita", beritaPass)
                        if (data.category == "bantuan"){
                            intent.putExtra("bantuan", true)
                        } else {
                            intent.putExtra("bantuan", false)
                        }
                        startActivity(intent)

                    }

                })
                val adapter = beritaAdapter
                recycleViewBerita.adapter = adapter
            } else {

            }
            dialog.dismiss()
        })


    }

    private fun callAdapter(){
        dialog()
        getBeritaViewModel.getBeritaKebencanaanPemerintah()!!.observe(this, Observer {
            Log.d("galihberitata", "berubah")
            if (!it.isEmpty()){
                var listBeritaKebencanaanPemerintah = ArrayList<BeritaGET>()
                it.forEach {thisBerita ->
                    listBeritaKebencanaanPemerintah.add(thisBerita)
                }
                val mutableList : MutableList<BeritaGET> = listBeritaKebencanaanPemerintah
                var beritaAdapter = BeritaDashboardAdapter(mutableList)

                beritaAdapter.setOnItemClickCallback(object : BeritaDashboardAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: BeritaGET) {
                        var beritaPass = BeritaGETLatLong(
                            data.id,
                            data.category,
                            data.title,
                            data.body,
                            data.author,
                            data.image,
                            data.uid_author,
                            data.government,
                            data.location.latitude,
                            data.location.longitude,
                            data.publish_date
                        )
                        var intent = Intent(context, BeritaDetail::class.java)
                        intent.putExtra("detail_berita", beritaPass)
                        intent.putExtra("bantuan", false)
                        startActivity(intent)

                    }

                })
                val adapter = beritaAdapter
                recycleViewBerita.adapter = adapter
            } else {

            }
            dialog.dismiss()
        })


    }

    fun dialog(){
        val padding  = 30
        val ll : LinearLayout = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(padding, padding, padding, padding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0,0, padding,0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText= TextView(context)
        tvText.text = "Loading ..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        dialog = builder.create()
        dialog.show()
        val window = dialog.window
        if (window != null){
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = 700
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }


}
