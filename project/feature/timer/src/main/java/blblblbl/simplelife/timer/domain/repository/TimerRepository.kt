package blblblbl.simplelife.timer.domain.repository

import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import java.util.*

interface TimerRepository {
    suspend fun getConfiguration(): Config?
    fun startTime(): Date?

    fun setStartTime(date: Date?)

    fun stopTime(): Date?

    fun setStopTime(date: Date?)

    fun timerCounting(): Boolean

    fun setTimerCounting(value: Boolean)

    fun setTimerInPause(value: Boolean)

    fun isTimerInPause(): Boolean

    fun pauseTime(): Date?

    fun setPauseTime(date: Date?)
    fun timerState(): TimerState?
    fun setTimerState(timerState: TimerState?)

    fun timerStage(): TimerStage?
    fun setTimerStage(timerStage: TimerStage?)

}