package blblblbl.simplelife.timer.domain.usecase

import android.util.Log
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.domain.repository.TimerRepository
import java.util.*
import javax.inject.Inject

class TimerActionsUseCase @Inject constructor(
    private val timerRepository: TimerRepository
) {
    fun getTimerState():TimerState? = timerRepository.timerState()

    fun getTimerStage(): TimerStage? = timerRepository.timerStage()

    fun setTimerStage(timerStage: TimerStage) = timerRepository.setTimerStage(timerStage)


    fun startTimer(timeTask:Long){
        val startTime = Date(System.currentTimeMillis())
        val stopTime = Date(System.currentTimeMillis()+timeTask)
        timerRepository.setStartTime(startTime)
        timerRepository.setStopTime(stopTime)
        timerRepository.setTimerCounting(true)
        timerRepository.setTimerState(TimerState.COUNTING)
    }
    fun resumeTimer(){
        val dif = System.currentTimeMillis()-timerRepository.pauseTime()!!.time
        val newStopTime = Date(timerRepository.stopTime()!!.time+dif)
        timerRepository.setStopTime(newStopTime)
        timerRepository.setTimerCounting(true)
        timerRepository.setTimerState(TimerState.COUNTING)
    }
    fun stopTimer(){
        timerRepository.setTimerCounting(false)
        timerRepository.setStartTime(null)
        timerRepository.setStopTime(null)
        timerRepository.setTimerInPause(false)
        timerRepository.setPauseTime(null)
        timerRepository.setTimerState(TimerState.STOP)
    }
    fun pauseTimer(){
        timerRepository.setTimerCounting(false)
        timerRepository.setTimerInPause(true)
        val pauseTime = System.currentTimeMillis()
        timerRepository.setPauseTime(Date(pauseTime))
        timerRepository.setTimerState(TimerState.PAUSE)
    }
    fun getTimeRemaining():Long = timerRepository.stopTime()!!.time - System.currentTimeMillis()
}