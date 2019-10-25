package id.trian.omkoro.view.cloudMessage

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.trian.omkoro.view.ui.family.FamilyMapActivity
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.ComponentName
import android.app.ActivityManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.RingtoneManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import id.trian.omkoro.view.ui.beranda.HomeActivity
import android.media.AudioAttributes
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.GeoPoint
import id.trian.omkoro.service.repository.FirebaseLoginRepository
import java.io.IOException


class NotifGempaService : FirebaseMessagingService() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("tokentoken", p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data.let {
            notification(it["title"].toString(), it["message"].toString())
        }
        // some codes here
    Handler(Looper.getMainLooper()).post {
        tsunamiNotification()
        }
    }

    fun notification(title: String, message: String){

        val random = (0..100).random()

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, random, intent, PendingIntent.FLAG_ONE_SHOT)
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel1")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorPrimaryDark))
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(message))
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel1", "notif",
                NotificationManager.IMPORTANCE_HIGH
            )
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            channel.enableVibration(true)
            channel.enableLights(true)

            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000, 1000, 1000,1000 ,1000 , 1000)
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(random, builder.build())


    }

    fun tsunamiNotification(){
        Log.d("galihloc", "masuk")
        try {
            Log.d("galihloc", "masuk2")
            mFusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    mLocation = task.result
                    val geoPoint = GeoPoint(mLocation!!.latitude, mLocation!!.longitude)
                    var firebaseDB = FirebaseLoginRepository()
                    firebaseDB.saveUserLocation(geoPoint)
                    Log.d("galihloc", geoPoint.toString())

                } else {
                    Log.d("galihloc", "err1")
                }
            }
        } catch (e: IOException){
            Log.d("galihloc", "err2")
        }


//        val intent = Intent(this, HomeActivity::class.java).apply {
//            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        }
//        intent.putExtra("fromNotifGempa", true)
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        var builder = NotificationCompat.Builder(this, "channel11")
//            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
//            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
//            .setContentTitle("Pemeriksaan Keselamatan")
//            .setContentText("Robert Downey dalam keadaan selamat dan baik-baik saja")
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .setSound(uri)
//
//        val mNotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            var channel = NotificationChannel(
//                "channel11", "notif",
//                NotificationManager.IMPORTANCE_HIGH
//            )
//
//
//        }
//
//        mNotificationManager.notify(1, builder.build())
    }


    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            val runningProcesses = am.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && processInfo.processName == context.packageName) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.packageName) {
                            isInBackground = false
                        }
                    }
                }
            }
        } else {
            val taskInfo = am.getRunningTasks(1)
            val componentInfo = taskInfo[0].topActivity
            if (componentInfo.packageName == context.packageName) {
                isInBackground = false
            }
        }

        return isInBackground
    }
}
