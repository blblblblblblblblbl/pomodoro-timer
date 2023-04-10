package blblblbl.simplelife.timer.data.persistent_storage

import android.util.Log
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import java.util.*

interface TimerPersistentStorage {

    fun startTime(): Date?

    fun setStartTime(date: Date?)

    fun stopTime(): Date?

    fun setStopTime(date: Date?)

    fun timerCounting(): Boolean

    fun setTimerInPause(value: Boolean)

    fun isTimerInPause(): Boolean

    fun pauseTime(): Date?

    fun setPauseTime(date: Date?)

    fun setTimerCounting(value: Boolean)

    fun timerState(): TimerState?

    fun setTimerState(timerState: TimerState?)

    fun timerStage(): TimerStage?

    fun setTimerStage(timerStage: TimerStage?)

    fun getProgress(): Int?

    fun setProgress(progress: Int)


}