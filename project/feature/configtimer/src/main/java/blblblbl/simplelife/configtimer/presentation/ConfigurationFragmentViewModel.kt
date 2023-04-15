package blblblbl.simplelife.configtimer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.configtimer.domain.model.Config
import blblblbl.simplelife.configtimer.domain.usecase.GetConfigurationUseCase
import blblblbl.simplelife.configtimer.domain.usecase.SaveConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val saveConfigurationUseCase: SaveConfigurationUseCase
):ViewModel() {
    private val _savedConfig = MutableStateFlow<Config?>(null)
    val savedConfig = _savedConfig.asStateFlow()
    fun getConfig(){
        viewModelScope.launch {
            _savedConfig.value = getConfigurationUseCase.execute()
        }
    }
    fun saveConfig(config: Config){
        viewModelScope.launch {
            saveConfigurationUseCase.execute(config = config)
            _savedConfig.value = config
        }
    }
}