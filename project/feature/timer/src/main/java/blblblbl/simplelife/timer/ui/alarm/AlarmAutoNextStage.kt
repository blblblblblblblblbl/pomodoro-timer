package blblblbl.simplelife.timer.ui.alarm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.model.DayInfo
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.domain.usecase.GetConfigurationUseCase
import blblblbl.simplelife.timer.domain.usecase.HistoryUseCase
import blblblbl.simplelife.timer.domain.usecase.TimerActionsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

class AlarmAutoNextStage @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val timerActionsUseCase: TimerActionsUseCase,
    private val historyUseCase: HistoryUseCase
) {
    private val _timerConfiguration = MutableStateFlow<Config?>(null)

    private val _timerStage = MutableStateFlow<TimerStage?>(null)

    private val _timeTask = MutableStateFlow<Long?>(null)
    val timeTask = _timeTask.asStateFlow()
    fun getConfig(){
        CoroutineScope(SupervisorJob()).launch(EmptyCoroutineContext) {
            val config = getConfigurationUseCase.execute()
            _timerConfiguration.value = config
            _timeTask.value = (config?.workTime?.times(1000))?.toLong()
        }
    }
    fun getTimerStage(){
        _timerStage.value = timerActionsUseCase.getTimerStage()
    }
    init {
        getConfig()
        getTimerStage()
    }
    fun goToNextStage(){
        saveDayInfo()
        if(_timerStage.value== TimerStage.WORK){
            timerActionsUseCase.setTimerStage(TimerStage.RELAX)
            _timeTask.value = (_timerConfiguration.value?.relaxTime?.times(1000))?.toLong()
        }
        else{
            timerActionsUseCase.setTimerStage(TimerStage.WORK)
            _timeTask.value = (_timerConfiguration.value?.workTime?.times(1000))?.toLong()
        }
        stopTimer()
    }

    fun startTimer(context:Context){
        timeTask.value?.let {
            timerActionsUseCase.startTimer(it)
            AndroidAlarmScheduler(context).schedule(AlarmItem(LocalDateTime.now().plusSeconds(it/1000),"timer"))
        }

    }
    fun stopTimer(){
        timerActionsUseCase.stopTimer()
    }
    private fun saveDayInfo(){
        /*val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val plusWorkTime = if (timerStage.value== TimerStage.WORK) timeTask.value else 0
        val plusRelaxTime = if (timerStage.value== TimerStage.RELAX) timeTask.value else 0
        CoroutineScope(SupervisorJob()).launch(EmptyCoroutineContext) {
            val savedDayInfo = historyUseCase.getDayInfo(Date.valueOf(currentDate))
            val dayInfo = DayInfo(
                date = Date.valueOf(currentDate),
                totalWorkTime = (savedDayInfo?.totalWorkTime ?: 0) +plusWorkTime!!.toLong(),
                totalRelaxTime = (savedDayInfo?.totalRelaxTime ?: 0)+plusRelaxTime!!.toLong(),
                progress = (savedDayInfo?.progress ?: 0)+plusWorkTime!!.toLong(),
                goal = goal.value!!.toLong()*1000
            )
            historyUseCase.saveDayInfo(dayInfo)
        }
*/
    }
}