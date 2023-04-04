package blblblbl.simplelife.timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.usecase.GetConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase
) :ViewModel() {
    private val _timerConfiguration = MutableStateFlow<Config?>(null)
    val timerConfiguration = _timerConfiguration.asStateFlow()

    fun getConfig(){
        viewModelScope.launch {
            _timerConfiguration.value = getConfigurationUseCase.execute()
        }
    }
    fun startTimer(){

    }

    fun stopTimer(){

    }
}