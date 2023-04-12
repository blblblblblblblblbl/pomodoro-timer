package blblblbl.simplelife.settings.domain.usecase

import blblblbl.simplelife.settings.domain.model.AppConfiguration
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveAppConfigUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(config:AppConfiguration) =
        settingsRepository.saveConfig(config)
}