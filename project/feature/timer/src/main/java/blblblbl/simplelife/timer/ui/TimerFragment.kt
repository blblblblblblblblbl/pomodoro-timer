package blblblbl.simplelife.timer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.*
import blblblbl.simplelife.timer.presentation.TimerFragmentViewModel

@Composable
fun TimerFragment() {
    val viewModel: TimerFragmentViewModel = hiltViewModel()
    val context = LocalContext.current
    var time:Long by remember { mutableStateOf(0) }
    val timerReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            time = intent?.getLongExtra(TimerWorker.TIME_KEY,0)?:0
        }
    }
    context.registerReceiver(timerReceiver, IntentFilter(TimerWorker.TIMER_UPDATE))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        )
    {
        Text(text = time.toString())
        Button(onClick = {
            val timerWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
                .setInputData(
                    Data.Builder().build()
                )
                //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(context).enqueue(timerWorkRequest)
            /*val timer = object: CountDownTimer(30000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    val intent = Intent(TimerWorker.TIMER_UPDATE)
                    val secs = millisUntilFinished/1000
                    intent.putExtra(TimerWorker.TIME_KEY,secs)
                    context.sendBroadcast(intent)
                }
                override fun onFinish() {
                    //timerNotifications.finish()
                }
            }.start()*/
        }) {
            Text(text = "start")
        }
    }

}