package blblblbl.simplelife.timer.ui.alarm

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import blblblbl.simplelife.timer.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltWorker
class AlarmWorker@AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    //private val settingsRepository: SettingsRepository
    ) : Worker(context, params) {
   /* @Inject
    lateinit var settingsRepository: SettingsRepository*/
    val mp = MediaPlayer()

    override fun doWork(): Result {
        Log.d("MyLog","AlarmWorker:doWork")
        return try {
            runBlocking {
                play(context)
                delay(20000)
            }
            stopAlarm()
            Result.success()
        }
        catch (e:Throwable){
            Log.d("MyLog","alarm worker exception:${e.message}")
            Result.failure()
        }
    }

    override fun onStopped() {
        stopAlarm()
        super.onStopped()
    }
    private fun stopAlarm(){
        if (mp.isPlaying) {
            mp.stop()
        }
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
    }
    private fun play(context: Context?){
        val message = inputData.getString(AlarmReceiver.ALARM_TEXT_KEY) ?: return
        val sound = "android.resource://"+"blblblbl.simplelife.pomodorotimer"+"/raw/ringtone3"//settingsRepository.getConfig()?.alarmRingtone
        if (context != null) {
            Log.d("MyLog", "worker alarm play")
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
                isLooping = true
                start()
            }
            makeStatusNotification(message,context)
        }
    }
    fun makeStatusNotification(message: String, context: Context) {
        makeChannel(context)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.KIND_KEY, AlarmReceiver.KIND_OFF_ALARM)
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
            .setOngoing(true)

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
        const val ALARM_WORKER_TAG = "pomodoroalarmworkersound"
    }
}
@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notificationsa"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "DownloadWorkRequest"
const val CHANNEL_ID = "VERBOSE_NOTIFICATIONAA"
const val NOTIFICATION_ID = 1