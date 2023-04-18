package blblblbl.simplelife.timer.presentation

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimerFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val timerActionsUseCase: TimerActionsUseCase,
    private val historyUseCase: HistoryUseCase
) :ViewModel() {
    private val _timerConfiguration = MutableStateFlow<Config?>(null)
    val timerConfiguration = _timerConfiguration.asStateFlow()

    private val _timerState = MutableStateFlow<TimerState?>(null)
    val timerState = _timerState.asStateFlow()

    private val _timerStage = MutableStateFlow<TimerStage?>(null)
    val timerStage = _timerStage.asStateFlow()

    private val _time = MutableStateFlow<Long?>(null)
    val time = _time.asStateFlow()
    var timeJob:Job? = null

    private val _goal = MutableStateFlow<Int?>(null)
    val goal = _goal.asStateFlow()

    private val _progress = MutableStateFlow<Int?>(null)
    val progress = _progress.asStateFlow()
    private val _timeTask = MutableStateFlow<Long?>(null)
    val timeTask = _timeTask.asStateFlow()

    fun getConfig(){
        viewModelScope.launch {
            val config = getConfigurationUseCase.execute()
            _timerConfiguration.value = config
            _timeTask.value = (config?.workTime?.times(1000))?.toLong()
            _time.value = timeTask.value
            _goal.value = config?.goal
        }
    }
    fun getTimerState(){
        _timerState.value = timerActionsUseCase.getTimerState()
    }

    fun getTimerStage(){
        _timerStage.value = timerActionsUseCase.getTimerStage()
    }
    init {
        Log.d("MyLog","initial")
        getTimerState()
        getTimerStage()
        getTime()
        _progress.value = timerActionsUseCase.getProgress()
        if (_timerState.value==TimerState.COUNTING){
            timeJob = viewModelScope.launch {
                while (true){
                    _time.value = (timerActionsUseCase.getTimeRemaining())
                    delay(1000)
                }
            }
        }
    }

    fun setTimerStage(timerStage: TimerStage){
        _timerStage.value = timerStage
        timerActionsUseCase.setTimerStage(timerStage)
    }
    fun goToNextStage(){
        saveDayInfo()
        if(_timerStage.value==TimerStage.WORK){
            if (_progress.value!=null){
                Log.d("MyLog","_progress.value:${_progress.value}")
                Log.d("MyLog","timeTask.value:${timeTask.value}")
                _progress.value = _progress.value!!+ (timeTask.value?.toInt() ?: 0)
                val curProgress = timerActionsUseCase.getProgress()
                curProgress?.let { curProgress->
                    timerActionsUseCase.setProgress(curProgress+(timeTask.value?.toInt() ?: 0))
                }
            }
            timerActionsUseCase.setTimerStage(TimerStage.RELAX)
            _timerStage.value = TimerStage.RELAX
            _timeTask.value = (_timerConfiguration.value?.relaxTime?.times(1000))?.toLong()
        }
        else{
            timerActionsUseCase.setTimerStage(TimerStage.WORK)
            _timerStage.value = TimerStage.WORK
            _timeTask.value = (_timerConfiguration.value?.workTime?.times(1000))?.toLong()
        }
        stopTimer()
    }

    fun startTimer(){
        timeTask.value?.let { timerActionsUseCase.startTimer(it) }

        getTimerState()
        timeJob = viewModelScope.launch {
            while (true){
                _time.value = (timerActionsUseCase.getTimeRemaining())
                delay(1000)
            }
        }
    }
    fun getTime(){
        if (_timerState.value==TimerState.PAUSE){
            resumeTimer()
            pauseTimer()
        }
        else if (_timerState.value==TimerState.STOP){

        }
        else if (_timerState.value==TimerState.COUNTING){
            _time.value = (timerActionsUseCase.getTimeRemaining())
        }
    }
    fun stopTimer(){
        timeJob?.cancel()
        timerActionsUseCase.stopTimer()
        getTimerState()
        _time.value = _timeTask.value
    }
    fun pauseTimer(){
        timerActionsUseCase.pauseTimer()
        timeJob?.cancel()
        getTimerState()
    }
    fun resumeTimer(){
        timerActionsUseCase.resumeTimer()
        timeJob = viewModelScope.launch {
            while (true){
                _time.value = (timerActionsUseCase.getTimeRemaining())
                delay(1000)
            }
        }
        getTimerState()
    }
    fun resetProgress(){
        timerActionsUseCase.setProgress(0)
        _progress.value = 0
    }
    private fun saveDayInfo(){
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val plusWorkTime = if (timerStage.value==TimerStage.WORK) timeTask.value else 0
        val plusRelaxTime = if (timerStage.value==TimerStage.RELAX) timeTask.value else 0
        viewModelScope.launch(Dispatchers.IO) {
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

    }
}