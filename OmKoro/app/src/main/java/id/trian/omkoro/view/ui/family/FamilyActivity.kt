package id.trian.omkoro.view.ui.family

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.trian.omkoro.R
import id.trian.omkoro.viewmodel.ProfileViewModel
import id.trian.omkoro.service.model.*
import androidx.recyclerview.widget.RecyclerView
import id.trian.omkoro.viewmodel.FamilyViewModel
import androidx.appcompat.app.AppCompatDelegate
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.firestore.FirebaseFirestore
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import id.trian.omkoro.view.adapter.FamilyRequestAdapter
import kotlinx.android.synthetic.main.activity_family.*
import kotlinx.android.synthetic.main.dialog_detail_permintaan_tambah_keluarga.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Gravity
import android.widget.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.app.AlertDialog
import android.graphics.Color
import android.view.ViewGroup
import android.view.WindowManager
import id.trian.omkoro.view.adapter.FamilyListAdapter
import java.io.IOException
import java.lang.Exception


class FamilyActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var familyViewModel : FamilyViewModel
    lateinit var viewModel : ProfileViewModel
    lateinit var user: User
    lateinit var btnAdd : Button
    lateinit var recycleViewRequest : RecyclerView
    lateinit var recycleViewFamilyList : RecyclerView
    var firebaseRepository = FirebaseLoginRepository()
    var firestoreDB = FirebaseFirestore.getInstance()
    lateinit var dialog : AlertDialog
    private var dummy: ArrayList<User>  = ArrayList()
    private var dummyFamilylist: ArrayList<User>  = ArrayList()
    var dismissLoadingReq : Int = 0
    var dismissLoadingList : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_family)
        btnAdd = findViewById(R.id.family_btnAddFamily)
        btnAdd.setOnClickListener(this)

        recycleViewRequest = findViewById(R.id.family_rvRequest)
        recycleViewRequest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycleViewRequest.setHasFixedSize(true)
        recycleViewFamilyList = findViewById(R.id.family_rvList)
        recycleViewFamilyList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycleViewFamilyList.setHasFixedSize(true)

        user = User()
        familyViewModel = ViewModelProviders.of(this)
            .get(FamilyViewModel::class.java)
        viewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)

        //Action Bar Back
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Keluarga"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        dialog()
        getRequestList()
        getFamilyList()
        //getRecycleViewRequestList
        //getRequestList()


    }

    fun callAdapter(mutableList: MutableList<User>){
        val familyAdapter = FamilyRequestAdapter(mutableList)
        val adapter = familyAdapter

        familyAdapter.setOnItemClickCallback(object :FamilyRequestAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                dialogDetailPermintaanKeluarga(data)
            }
        })
        recycleViewRequest.adapter = adapter
    }

    fun callAdapterFamily(mutableList: MutableList<User>){
        val familyAdapter = FamilyListAdapter(mutableList)
        val adapter = familyAdapter

        familyAdapter.setOnItemClickCallback(object :FamilyListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                //dialogDetailPermintaanKeluarga(data)
            }
        })
        recycleViewFamilyList.adapter = adapter
    }

    private fun getFamilyList(){
        familyViewModel.getFamilyCollection()?.observe(this, Observer {
            Log.d("galihtes", it.toString())
            if (it.isNotEmpty() && it[0] != "thisisnull"){
                CoroutineScope(Dispatchers.Main).launch {
                    val user = firebaseRepository.getAllProfile().await()
                    val alldata = user.toObjects(User::class.java)
                    val selectdata = it

                    alldata.forEach {
                        for (thisdata in selectdata){
                            if (thisdata == it.uid){
                                dummyFamilylist.add(it)
                            }
                        }
                    }
                    callAdapterFamily(dummyFamilylist)
                    dismissLoadingList++
                    dismissDialog()
                }
            } else{
                Log.d("galihtes", "negg")
                dismissLoadingList++
                dismissDialog()
            }

        })
    }

    private fun getRequestList(){
        try {
            familyViewModel.getRequestCollection()?.observe(this, Observer {
                if (!it.isEmpty() && it[0].uid != "thisisnull"){
                    family_layoutRequest.visibility = View.VISIBLE
                    CoroutineScope(Dispatchers.Main).launch{
                        val user = firebaseRepository.getAllProfile().await()
                        val alldata = user.toObjects(User::class.java)
                        val selecteddata = it

                        alldata.forEach {
                            for (thisdata in selecteddata){
                                if (thisdata.uid == it.uid){
                                    dummy.add(it)
                                }
                            }
                        }
                        callAdapter(dummy)
                        dismissLoadingReq++
                        dismissDialog()
                    }
                } else {
                    family_layoutRequest.visibility = View.GONE
                    dismissLoadingReq++
                    dismissDialog()
                }

            })

        } catch (e: Exception){
            dismissLoadingReq++
            dismissDialog()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btnAdd.id -> {
                dialogSearchNip()
            }
        }
    }

    fun dialogSearchNip(){
        MaterialDialog.Builder(this)
            .title("Tambah Keluarga")
            .content("Masukkan NIP Keluarga")
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input("NIP", "", MaterialDialog.InputCallback { dialog, input ->
                Log.d("galiha","1ww")
                returntheaddfamily(input.toString())
            })
            .show()
    }

    fun dialogDetailPermintaanKeluarga(user: User){
        MaterialDialog.Builder(this)
            .title("Detail")
            .content("Apakah anda Yakin untuk menambahkan?")
            .positiveText("Ya")
            .negativeText("Tidak")
            .onPositive { dialog, which ->
                addRequestToFamilyList(user)
            }
            .onNegative { dialog, which ->
                deleteRequestFamilyList(user)
            }
            .show()
    }

    fun returntheaddfamily(input : String){
        CoroutineScope(Dispatchers.Main).launch{
            val add = familyViewModel.addRequestFamily(input)
            when (add) {
                true -> {
                    Toast.makeText(applicationContext, "Berhasil menambahkan keluarga !", Toast.LENGTH_SHORT).show()
                }
                false -> Toast.makeText(applicationContext, "Gagal menambahkan keluarga !", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun decodeBase64(input: String): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }


    fun dialog(){
        val padding  = 30
        val ll : LinearLayout = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(padding, padding, padding, padding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0,0, padding,0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText= TextView(this)
        tvText.text = "Loading ..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(this)
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


    fun addRequestToFamilyList(user: User){
        try {
            familyViewModel.acceptRequestFamily(user).observeForever {
                Log.d("galihfamily", it.toString())
                if (it!= null){
                    if (it){
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                }
            }
        } catch (e : IOException){

        }
    }

    fun deleteRequestFamilyList(user: User){
        try {
            familyViewModel.deleteRequestFamily(user.uid)
        } catch (e: IOException){

        }
    }

    fun dismissDialog(){
        if (dismissLoadingList != 0 && dismissLoadingReq !=0){
            dialog.dismiss()
            dismissLoadingList = 0
            dismissLoadingReq = 0
        }
    }
}
