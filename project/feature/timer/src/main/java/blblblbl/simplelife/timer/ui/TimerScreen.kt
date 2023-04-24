package blblblbl.simplelife.timer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.ui.alarm.AlarmItem
import blblblbl.simplelife.timer.ui.alarm.AlarmReceiver
import blblblbl.simplelife.timer.ui.alarm.AlarmWorker
import blblblbl.simplelife.timer.ui.util.toHhMmSs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerScreen(
    timeFlow: StateFlow<Long?>,
    stateFlow: StateFlow<TimerState?>,
    stageFlow: StateFlow<TimerStage?>,
    timeTaskFlow: StateFlow<Long?>,
    goalFlow: StateFlow<Int?>,
    progressFlow: StateFlow<Int?>,
    startOnCLick:()->Unit,
    stopOnCLick:()->Unit,
    pauseOnCLick:()->Unit,
    resumeOnCLick:()->Unit,
    settingsOnClick:()->Unit,
    nextStageOnCLick:()->Unit,
    resetProgressOnclick:()->Unit,
    menuOnclick:()->Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    )
    {

        val time by timeFlow.collectAsState()
        val timeTask by timeTaskFlow.collectAsState()
        val state by stateFlow.collectAsState()
        val stage by stageFlow.collectAsState()

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Box(modifier = Modifier.fillMaxWidth()){
                    IconButton(onClick = {menuOnclick()}, modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(Icons.Default.Menu, contentDescription = "menu button",modifier = Modifier.size(48.dp))
                    }
                }
                val progress = ((time?:0).toFloat() / ((timeTask?:1).toFloat())).coerceAtLeast(0f)+1
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
                if (state==TimerState.STOP){
                    Box(modifier = Modifier.fillMaxWidth()){
                        IconButton(onClick = { settingsOnClick() }, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Icon(Icons.Default.Settings, contentDescription = "settings button",Modifier.size(48.dp))
                        }
                    }

                }
            }
        }
        Card(shape = CircleShape) {
            Text(text = stage.toString(), modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp), style = MaterialTheme.typography.headlineLarge)
        }
        GoalAndProgress(
            goalFlow = goalFlow,
            progressFlow = progressFlow,
            showResetButton = state==TimerState.STOP,
            resetOnClick = resetProgressOnclick)
        if (state==TimerState.STOP){
            Button(onClick = {
                startOnCLick()
            }) {
                Text(text = "start", style = MaterialTheme.typography.headlineLarge)
            }
        }
        else if (state==TimerState.PAUSE){
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(onClick = {
                    resumeOnCLick()
                }) {
                    Text(text = "resume", style = MaterialTheme.typography.headlineLarge)
                }
                Button(onClick = {
                    stopOnCLick()
                }) {
                    Text(text = "stop", style = MaterialTheme.typography.headlineLarge)
                }
            }

        }
        else if (state==TimerState.COUNTING){
            time?.let { time->
                if(time<0){
                    Button(onClick = {
                        nextStageOnCLick()
                    }) {
                        Text(text = "next stage", style = MaterialTheme.typography.headlineLarge)
                    }
                }
                else{
                    Button(onClick = {
                        pauseOnCLick()
                    }) {
                        Text(text = "pause", style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainTimer(
    animatedProgress: Float,
    timerVisibility: Boolean,
    timerScreenState: TimerState,
    modifier: Modifier,
    formattedTime: String,
    onOptionTimerClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val progressVisibility =
            if (timerScreenState == TimerState.STOP) timerVisibility else true
        if (progressVisibility) {
            CircularProgressWithThumb(
                progress = animatedProgress,
                strokeWidth = 4.dp,
                thumbSize = 6.dp,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(modifier = Modifier.align(Alignment.Center)) {
            content()
        }
    }
}

@Composable
fun CircularProgressWithThumb(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    thumbSize: Dp? = null
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }
    val thumbSizeInPx = with(LocalDensity.current) { thumbSize?.toPx() } ?: stroke.width
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(40.dp)
            .focusable()
    ) {
        val startAngle = 270f
        val sweep = progress * 360f
        val diameterOffset = stroke.width / 2
        drawArcBackground(
            startAngle,
            (1 - progress) * 360,
            backgroundColor,
            stroke,
            diameterOffset
        )
        drawArcIndicator(startAngle, sweep, color, stroke, diameterOffset)
        drawThumb(thumbSizeInPx, color, diameterOffset, sweep, startAngle)
    }
}

fun DrawScope.drawThumb(
    dotSizeInPx: Float,
    color: Color,
    diameterOffset: Float,
    sweep: Float,
    startAngle: Float
) {
    val internalCenter = Offset(size.width / 2, size.width / 2)
    val radius = size.width / 2 - diameterOffset
    val mThumbX = (internalCenter.x + radius * cos(-(startAngle - sweep) * Math.PI / 180))
    val mThumbY = (internalCenter.y - radius * sin(-(startAngle - sweep) * Math.PI / 180))
    val middle = Offset(mThumbX.toFloat(), mThumbY.toFloat())
    drawCircle(color = color, radius = dotSizeInPx, center = middle)
}

fun DrawScope.drawArcBackground(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
    diameterOffset: Float
) {
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

fun DrawScope.drawArcIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
    diameterOffset: Float
) {
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = -sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

@Composable
fun GoalAndProgress(
    goalFlow: StateFlow<Int?>,
    progressFlow: StateFlow<Int?>,
    showResetButton:Boolean,
    resetOnClick:()->Unit
){
    val goal by goalFlow.collectAsState()
    val progress by progressFlow.collectAsState()
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column() {
                Text(text = "goal:${((goal?:0)*1000).toLong()?.toHhMmSs()}", style = MaterialTheme.typography.headlineLarge)
                Text(text = "progress:${((progress?:0)).toLong()?.toHhMmSs()}", style = MaterialTheme.typography.headlineLarge)
            }
            if (showResetButton){
                IconButton(onClick = { resetOnClick() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "reset progress button",Modifier.size(48.dp))
                }
            }
        }
    }
}
@Preview
@Composable
fun ResetIconPreview(){
    Icon(Icons.Default.Refresh, contentDescription = "reset progress button")
}
@Preview
@Composable
fun GoalAndProgressPreview(){
    val _goal = MutableStateFlow<Int?>(null)
    val goal = _goal.asStateFlow()
    _goal.value = 5
    val _progress = MutableStateFlow<Int?>(null)
    val progress = _progress.asStateFlow()
    _progress.value = 5
    val showResetButton = true
    GoalAndProgress(
        goalFlow = goal,
        progressFlow = progress,
        showResetButton = true,
        resetOnClick = {}
    )
}