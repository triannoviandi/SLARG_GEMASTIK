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
import android.R
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







class NotifGempaService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        message.data.let {
            notification(it["title"].toString(), it["message"].toString())
        }
    }

    fun notification(title: String, message: String){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel1")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle(title)
            //.setContentText()
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

        mNotificationManager.notify(0, builder.build())
    }

    fun notification1(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel11")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Pemeriksaan Keselamatan")
            .setContentText("Robert Downey dalam keadaan selamat dan baik-baik saja")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                "channel11", "notif",
                NotificationManager.IMPORTANCE_HIGH
            )


        }

        mNotificationManager.notify(1, builder.build())
    }

    fun notification2(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel12")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Pemeriksaan Keselamatan")
            .setContentText("Chris Evans dalam keadaan selamat dan baik-baik saja")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                "channel12", "notif",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.vibrationPattern = longArrayOf(300, 300, 300)
            channel.setSound(uri, attributes)

            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(2, builder.build())
    }

    fun notification3(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel13")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Pemeriksaan Keselamatan")
            .setContentText("Brie Larsony dalam keadaan selamat dan baik-baik saja")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel13", "notif",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(3, builder.build())
    }

    fun notification4(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 4, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel14")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Pemeriksaan Keselamatan")
            .setContentText("Ryan Reynolds dalam keadaan selamat dan baik-baik saja")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel14", "notif",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(4, builder.build())
    }

    fun notification5(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 5, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel15")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Pemeriksaan Keselamatan")
            .setContentText("Scarlett Johansson dalam keadaan selamat dan baik-baik saja")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel15", "notif",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(5, builder.build())
    }


    fun notification6(){

        val intent = Intent(this, HomeActivity::class.java).apply {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("fromNotifGempa", true)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 5, intent, PendingIntent.FLAG_ONE_SHOT)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder = NotificationCompat.Builder(this, "channel15")
            .setSmallIcon(id.trian.omkoro.R.drawable.ic_stat_app_icon)
            .setColor(ContextCompat.getColor(applicationContext, id.trian.omkoro.R.color.colorRed))
            .setContentTitle("Peringatan !")
            .setContentText("Anda berada di Lokasi rawan tsunami tinggi, Segera berpindah ke lokasi lebih aman")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uri)

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel15", "notif",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(5, builder.build())
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
