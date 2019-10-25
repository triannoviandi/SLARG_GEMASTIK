package id.trian.omkoro.view.ui.beranda

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import id.trian.omkoro.viewmodel.HomeViewModel
import id.trian.omkoro.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_beranda.*
import kotlinx.android.synthetic.main.fragment_home.*
import id.trian.omkoro.view.ui.login.*
import id.trian.omkoro.R
import id.trian.omkoro.service.model.User
import id.trian.omkoro.view.ui.family.*
import id.trian.omkoro.view.ui.evakuasi.*
import id.trian.omkoro.view.ui.emergencyCall.*
import id.trian.omkoro.view.ui.panduan.*
import id.trian.omkoro.service.model.*
import java.io.ByteArrayOutputStream
import id.trian.omkoro.view.adapter.*
import id.trian.omkoro.viewmodel.GetBeritaViewModel

class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var homeViewModel: HomeViewModel
    lateinit var viewModel : ProfileViewModel
    lateinit var linearLayout: LinearLayout
    lateinit var dialog : AlertDialog

    lateinit var getBeritaViewModel : GetBeritaViewModel
    lateinit var recycleViewBerita : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getBeritaViewModel = ViewModelProviders.of(this)
            .get(GetBeritaViewModel::class.java)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_menuFamilyLocation.setOnClickListener(this)
        cv_menu1.setOnClickListener(this)
        cv_menu2.setOnClickListener(this)
        cv_menu3.setOnClickListener(this)
        cv_menu4.setOnClickListener(this)

        recycleViewBerita = view.findViewById(R.id.home_rvBerita)
        recycleViewBerita.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)

        getProfile()
        checkStateGempa()
        callAdapter()

        home_btnProfile.setOnClickListener {
            activity!!.startActivity(Intent(context,
                ProfileSettingActivity::class.java
            ))
        }
    }

    private fun callAdapter(){
        dialog()
        getBeritaViewModel.getBeritaKebencanaanPemerintah()!!.observe(this, Observer {
            if (!it.isEmpty()){
                var listBeritaKebencanaanPemerintah = ArrayList<BeritaGET>()
                it.forEach {thisBerita ->
                    listBeritaKebencanaanPemerintah.add(thisBerita)
                }
                val mutableList : MutableList<BeritaGET> = listBeritaKebencanaanPemerintah
                var beritaAdapter = BeritaHomeAdapter(mutableList)

                beritaAdapter.setOnItemClickCallback(object : BeritaHomeAdapter.OnItemClickCallback{
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
                dialog.dismiss()
                val adapter = beritaAdapter
                recycleViewBerita.adapter = adapter
            } else {
                dialog.dismiss()
            }
            //dialog.dismiss()
        })
    }

    private fun getProfile(){
        viewModel.checkExist().observe(this, Observer {
            if (it){
                viewModel.getProfile().observe(this, Observer {
                    home_imgPhoto.setImageBitmap(decodeBase64(it.fotoProfile))
                    home_tvNama.text = it.nama
                    home_tvLokasi.text = it.kota + ", " + it.provinsi
                })
            } else {
                Log.d("wewe", "Tidak ada data")
                Toast.makeText(context, "Mohon Setting Profile Anda", Toast.LENGTH_SHORT).show()
            }
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


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.cv_menu1 -> {
                activity!!.startActivity(Intent(context, FamilyActivity::class.java))
                Log.d("wewe", "samap")
            }
            R.id.cv_menu2 -> {
                activity!!.startActivity(Intent(context, EvakuasiActivity::class.java))
            }
            R.id.cv_menu3 -> {
                activity!!.startActivity(Intent(context, EmergencyCallActivity::class.java))
            }
            R.id.cv_menuFamilyLocation -> {
                activity!!.startActivity(Intent(context, FamilyMapActivity::class.java))
            }
            R.id.cv_menu4 -> {
                activity!!.startActivity(Intent(context, PanduanActivity::class.java))
            }
        }
    }

    fun checkStateGempa(){
        homeViewModel.state_gempa().observe(this, Observer {
            if (it){
                home_locationLayout.visibility = View.VISIBLE
            } else {
                home_locationLayout.visibility = View.GONE
            }
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