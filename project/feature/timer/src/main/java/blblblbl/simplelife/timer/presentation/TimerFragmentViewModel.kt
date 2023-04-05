package blblblbl.simplelife.timer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import blblblbl.simplelife.timer.domain.usecase.GetConfigurationUseCase
import blblblbl.simplelife.timer.domain.usecase.TimerActionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val timerActionsUseCase: TimerActionsUseCase
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

    fun getConfig(){
        viewModelScope.launch {
            _timerConfiguration.value = getConfigurationUseCase.execute()
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

    fun startTimer(){
        timerActionsUseCase.startTimer(30*1000)

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
}