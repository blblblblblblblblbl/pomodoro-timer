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
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import blblblbl.simplelife.timer.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val kind = intent?.getStringExtra(KIND_KEY) ?: return
        when(kind){
            KIND_PLAY_ALARM->{
                val message = intent?.getStringExtra(ALARM_TEXT_KEY) ?: return
                val alarmWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
                    .setInputData(
                        Data.Builder()
                            .putString(ALARM_TEXT_KEY,message)
                            .build())
                    .addTag(AlarmWorker.ALARM_WORKER_TAG)
                    .build()
                if (context != null) {
                    WorkManager
                        .getInstance(context)
                        .enqueue(alarmWorkRequest)
                }
                Log.d("MyLog","equeue(alarmWorkRequest)")
            }
            KIND_OFF_ALARM->{
                context?.let { context ->
                    stopAlarm(context)
                }

            }
        }

    }
    private fun stopAlarm(context: Context){
        WorkManager.getInstance(context).cancelAllWorkByTag(AlarmWorker.ALARM_WORKER_TAG)
        Log.d("MyLog"," AlarmReceiver stopAlarm")
    }

    companion object{
        const val KIND_KEY = "KIND"
        const val KIND_PLAY_ALARM = "KIND_PLAY"
        const val ALARM_TEXT_KEY = "ALARM_TEXT"
        const val KIND_OFF_ALARM = "KIND_OFF_ALARM"
    }
}
