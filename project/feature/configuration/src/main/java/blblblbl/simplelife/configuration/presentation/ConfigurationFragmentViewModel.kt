package blblblbl.simplelife.configuration.presentation

import androidx.lifecycle.ViewModel
import blblblbl.simplelife.configuration.domain.usecase.GetConfigurationUseCase
import blblblbl.simplelife.configuration.domain.usecase.SaveConfigurationUseCase
import javax.inject.Inject

class ConfigurationFragmentViewModel @Inject constructor(
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val saveConfigurationUseCase: SaveConfigurationUseCase
):ViewModel() {

}