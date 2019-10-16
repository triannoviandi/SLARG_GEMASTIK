package id.trian.omkoro.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import id.trian.omkoro.view.ui.beranda.BeritaMasyarakatFragment
import id.trian.omkoro.view.ui.beranda.BeritaPemerintahFragment

class BeritaPageAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        BeritaPemerintahFragment(),
        BeritaMasyarakatFragment()
    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pemerintah"
            else -> "Masyarakat"
        }
    }
}