package blblblbl.simplelife.timer.ui.alarm

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import blblblbl.simplelife.timer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var settingsRepository: SettingsRepository
    val mp = MediaPlayer()
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MyLog","mp.hash${mp.hashCode()}")
        val kind = intent?.getStringExtra(KIND_KEY) ?: return
        when(kind){
            KIND_PLAY_ALARM->{
                play(context,intent)
            }
            KIND_OFF_ALARM->{
                stopAlarm()
            }
        }

    }
    private fun play(context: Context?, intent: Intent?){
        Log.d("MyLog","mp.hash${mp.hashCode()}")
        val message = intent?.getStringExtra(ALARM_TEXT_KEY) ?: return
        val sound = settingsRepository.getConfig()?.alarmRingtone
        Log.d("MyLog","Alarm triggered: $message")
        println("Alarm triggered: $message")
        if (context != null) {
            mp.apply {
                reset()
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(context, Uri.parse(sound))
                prepare()
                start()
            }
            makeStatusNotification(message,context)
            val vib: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(10000)
        }
    }
    private fun stopAlarm(){
        Log.d("MyLog","mp.hash${mp.hashCode()}")
        if (mp.isPlaying) mp.pause()
    }
    fun makeStatusNotification(message: String, context: Context) {
        makeChannel(context)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(KIND_KEY, KIND_OFF_ALARM)
        }
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else
                0
        val stopIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flag
        )
        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_timer_24)
            .setSound(Uri.parse("android.resource://"+context.packageName +"/raw/ringtone"))
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .addAction(R.drawable.baseline_alarm_off_24,"STOP",stopIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVibrate(LongArray(0))

        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }
    fun makeChannel(context: Context){
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                setSound(
                    Uri.parse("android.resource://"+context.packageName +"/raw/ringtone"),
                    Notification.AUDIO_ATTRIBUTES_DEFAULT
                )
            }
            channel.description = description

            // Add the channel
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }
    companion object{
        const val KIND_KEY = "KIND"
        const val KIND_PLAY_ALARM = "KIND_PLAY"
        const val ALARM_TEXT_KEY = "ALARM_TEXT"
        const val KIND_OFF_ALARM = "KIND_OFF_ALARM"
    }
}
@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notificationsa"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "DownloadWorkRequest"
const val CHANNEL_ID = "VERBOSE_NOTIFICATIONAA"
const val NOTIFICATION_ID = 1