package blblblbl.simplelife.timer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
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
import blblblbl.simplelife.timer.domain.model.TimerState
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerScreen(){
    /*val current :Int = 0
    val time :Int = 30
    val progress = (current.toFloat() / time.toFloat()).coerceAtLeast(0f)
    val progressOffset = (1 - progress)
    val animatedProgress by animateFloatAsState(
        targetValue = progressOffset,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    MainTimer(
        animatedProgress = animatedProgress,
        timerVisibility = true,
        timerScreenState = ,
        modifier = ,
        formattedTime =
    ) {

    }*/
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