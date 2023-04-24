package blblblbl.simplelife.timer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import kotlin.math.roundToInt

@Composable
fun TimerFragment(
    onSettingsClicked: ()->Unit,
    menuOnClick: ()->Unit
) {
    val viewModel: TimerFragmentViewModel = hiltViewModel()
    viewModel.getConfig()
    val config by viewModel.timerConfiguration.collectAsState()
    val context = LocalContext.current
    val alarm = AndroidAlarmScheduler(context)
    Box(modifier = Modifier.padding(24.dp)) {
        TimerScreen(
            timeFlow = viewModel.time,
            stateFlow = viewModel.timerState,
            stageFlow = viewModel.timerStage,
            timeTaskFlow = viewModel.timeTask,
            goalFlow = viewModel.goal,
            progressFlow = viewModel.progress,
            startOnCLick =
            {
                if (config==null){
                    Toast.makeText(context,"first configure timer, tap on configure button",Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.timeTask.value?.let {
                        viewModel.startTimer()
                        alarm.schedule(AlarmItem(LocalDateTime.now().plusSeconds(it/1000),"timer"))
                    }

                }
            },
            stopOnCLick = { viewModel.stopTimer()},
            pauseOnCLick =
            {
                viewModel.pauseTimer()
                alarm.cancel()
            },
            resumeOnCLick = { viewModel.resumeTimer() },
            settingsOnClick = {onSettingsClicked()},
            nextStageOnCLick = {viewModel.goToNextStage()},
            resetProgressOnclick = {viewModel.resetProgress()},
            menuOnclick = menuOnClick
        )
    }



}
