package blblblbl.simplelife.timer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.work.*
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.presentation.TimerFragmentViewModel
import blblblbl.simplelife.timer.ui.alarm.AlarmItem
import blblblbl.simplelife.timer.ui.alarm.AndroidAlarmScheduler
import java.time.LocalDateTime
import java.util.*

@Composable
fun TimerFragment() {
    val viewModel: TimerFragmentViewModel = hiltViewModel()
    val context = LocalContext.current
    val alarm = AndroidAlarmScheduler(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        )
    {

        val time by viewModel.time.collectAsState()
        val state by viewModel.timerState.collectAsState()
        val stage by viewModel.timerStage.collectAsState()
        Text(text = stage.toString())
        Text(text = time.toString())
        if (state==TimerState.STOP){
            Button(onClick = {
                viewModel.startTimer()
                alarm.schedule(AlarmItem(LocalDateTime.now().plusSeconds(5),"timer"))
            }) {
                Text(text = "start")
            }
        }
        else if (state==TimerState.PAUSE){
            Row() {
                Button(onClick = {
                    viewModel.resumeTimer()
                }) {
                    Text(text = "resume")
                }
                Button(onClick = {
                    viewModel.stopTimer()

                }) {
                    Text(text = "stop")
                }
            }

        }
        else if (state==TimerState.COUNTING){
            Button(onClick = {
                viewModel.pauseTimer()
                alarm.cancel()
            }) {
                Text(text = "pause")
            }
        }

    }

}