package blblblbl.simplelife.timer.data.repository

import android.util.Log
import blblblbl.simplelife.timer.data.persistent_storage.ConfigurationPersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.TimerPersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.TimerPersistentStorageImpl
import blblblbl.simplelife.timer.data.utils.mapToDomain
import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.domain.repository.TimerRepository
import java.util.*
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val persistentStorage: ConfigurationPersistentStorage,
    private val timerPersistentStorage: TimerPersistentStorage
): TimerRepository {

    override suspend fun getConfiguration(): Config =
        persistentStorage.getConfig()?.mapToDomain()?: Config(null,null,null)

    override fun startTime(): Date? = timerPersistentStorage.startTime()

    override fun setStartTime(date: Date?) = timerPersistentStorage.setStartTime(date)

    override fun stopTime(): Date? = timerPersistentStorage.stopTime()

    override fun setStopTime(date: Date?) = timerPersistentStorage.setStopTime(date)

    override fun timerCounting(): Boolean = timerPersistentStorage.timerCounting()

    override fun setTimerCounting(value: Boolean) = timerPersistentStorage.setTimerCounting(value)

    override fun setTimerInPause(value: Boolean) = timerPersistentStorage.setTimerInPause(value)

    override fun isTimerInPause(): Boolean = timerPersistentStorage.isTimerInPause()

    override fun pauseTime(): Date? = timerPersistentStorage.pauseTime()

    override fun setPauseTime(date: Date?) = timerPersistentStorage.setPauseTime(date)

    override fun timerState(): TimerState? = timerPersistentStorage.timerState()

    override fun setTimerState(timerState: TimerState?) = timerPersistentStorage.setTimerState(timerState)

    override fun timerStage(): TimerStage? = timerPersistentStorage.timerStage()

    override fun setTimerStage(timerStage: TimerStage?) = timerPersistentStorage.setTimerStage(timerStage)



}