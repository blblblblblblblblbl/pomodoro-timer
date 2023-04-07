package blblblbl.simplelife.configtimer.presentation

import androidx.lifecycle.ViewModel
import blblblbl.simplelife.configtimer.domain.usecase.GetConfigurationUseCase
import blblblbl.simplelife.configtimer.domain.usecase.SaveConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigurationFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val saveConfigurationUseCase: SaveConfigurationUseCase
):ViewModel() {

}