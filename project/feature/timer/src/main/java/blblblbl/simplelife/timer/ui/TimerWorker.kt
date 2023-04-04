package blblblbl.simplelife.timer.ui

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import blblblbl.simplelife.timer.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted private val ctx: Context,
    @Assisted params: WorkerParameters,
    //private val timerNotifications: TimerNotifications
) : CoroutineWorker(ctx, params) {
    lateinit var timer:CountDownTimer
    override suspend fun doWork(): Result {
        val progress = "Starting Download"
        setForeground(getForegroundInfo())
        object:CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val intent = Intent(TIMER_UPDATE)
                val secs = millisUntilFinished/1000
                intent.putExtra(TIME_KEY,secs)
                ctx.sendBroadcast(intent)
            }
            override fun onFinish() {
                //timerNotifications.finish()
            }
        }.start()
        return Result.success()
    }
    

    companion object{
        const val TIMER_UPDATE = "pomodoro_timer_update"
        const val TIME_KEY = "pomodoro_timer_update_time_key"
        const val TIMER_FINISH = "pomodoro_timer_finish"
    }
}