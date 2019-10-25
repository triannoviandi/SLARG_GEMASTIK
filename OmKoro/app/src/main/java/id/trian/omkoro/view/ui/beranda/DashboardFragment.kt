package id.trian.omkoro.view.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import id.trian.omkoro.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import id.trian.omkoro.view.adapter.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import android.graphics.Typeface
import id.trian.omkoro.*


class DashboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.berita_viewPager.offscreenPageLimit= 2
        view.berita_viewPager.adapter = BeritaPageAdapter(fragmentManager!!)
        view.tablayout.setupWithViewPager(berita_viewPager)
        view.berita_buttonBuatBerita.setOnClickListener {
            activity!!.startActivity(Intent(view.context, BeritaInput::class.java))
        }

     //   setCustomFont()
    }

    fun setCustomFont() {



        val vg = tablayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup

            val tabChildsCount = vgTab.childCount

            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    tabViewChild.typeface =
                        Typeface.createFromAsset(context!!.assets, "font/opensans_light")
                }
            }
        }
    }


}