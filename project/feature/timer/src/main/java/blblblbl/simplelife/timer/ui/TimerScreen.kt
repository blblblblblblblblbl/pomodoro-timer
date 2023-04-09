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
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.ui.alarm.AlarmItem
import blblblbl.simplelife.timer.ui.util.toHhMmSs
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerScreen(
    timeFlow: StateFlow<Long?>,
    stateFlow: StateFlow<TimerState?>,
    stageFlow: StateFlow<TimerStage?>,
    timeTaskFlow: StateFlow<Long?>,
    startOnCLick:()->Unit,
    stopOnCLick:()->Unit,
    pauseOnCLick:()->Unit,
    resumeOnCLick:()->Unit,
    settingsOnClick:()->Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {

        val time by timeFlow.collectAsState()
        val timeTask by timeTaskFlow.collectAsState()
        val state by stateFlow.collectAsState()
        val stage by stageFlow.collectAsState()


        val progress = ((time?:0).toFloat() / ((timeTask?:1).toFloat())).coerceAtLeast(0f)
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
                startOnCLick()
            }) {
                Text(text = "start")
            }
            IconButton(onClick = { settingsOnClick() }) {
                Icon(Icons.Default.Settings, contentDescription = "settings button")
            }
        }
        else if (state==TimerState.PAUSE){
            Row() {
                Button(onClick = {
                    resumeOnCLick()
                }) {
                    Text(text = "resume")
                }
                Button(onClick = {
                    stopOnCLick()

                }) {
                    Text(text = "stop")
                }
            }

        }
        else if (state==TimerState.COUNTING){
            Button(onClick = {
                pauseOnCLick()
            }) {
                Text(text = "pause")
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