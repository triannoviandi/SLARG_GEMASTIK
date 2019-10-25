package id.trian.omkoro.view.ui.panduan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.trian.omkoro.R
import kotlinx.android.synthetic.main.activity_panduan.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Color
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class PanduanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panduan)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Panduan Kesiagapan Gempa Bumi dan Tsunami"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        pdfView()
    }

    fun pdfView(){
        pdfView
            .fromAsset("split.pdf")
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
            .invalidPageColor(Color.WHITE) // color of page that is invalid and cannot be loaded
            .load()
    }
}
