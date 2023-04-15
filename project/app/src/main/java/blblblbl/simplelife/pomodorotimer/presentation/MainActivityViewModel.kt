package blblblbl.simplelife.pomodorotimer.presentation

import androidx.lifecycle.ViewModel
import blblblbl.simplelife.settings.domain.model.AppConfiguration
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
):ViewModel(){
    fun getSettingsFlow():StateFlow<AppConfiguration?> = settingsRepository.config

}