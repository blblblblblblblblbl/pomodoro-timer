package blblblbl.simplelife.timer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.work.*
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.presentation.TimerFragmentViewModel
import blblblbl.simplelife.timer.ui.alarm.AlarmItem
import blblblbl.simplelife.timer.ui.alarm.AndroidAlarmScheduler
import blblblbl.simplelife.timer.ui.util.toHhMmSs
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


        val progress = ((time?:0).toFloat() / (30000.toFloat())).coerceAtLeast(0f)
        val progressOffset = (1 - progress)
        val animatedProgress by animateFloatAsState(
            targetValue = progressOffset,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        MainTimer(
            animatedProgress = animatedProgress,
            timerVisibility = true,
            timerScreenState = state!!,
            modifier = Modifier.size(200.dp),
            formattedTime ="",
            onOptionTimerClick = {}
        )
        {
            Text(text = time?.toHhMmSs().toString(), style = MaterialTheme.typography.headlineLarge)
        }
        Card(shape = CircleShape) {
            Text(text = stage.toString(), modifier = Modifier.padding(10.dp))
        }
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
