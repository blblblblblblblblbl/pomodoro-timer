package blblblbl.simplelife.settings.domain.repository

import blblblbl.simplelife.settings.domain.model.AppConfiguration
import kotlinx.coroutines.flow.StateFlow


interface SettingsRepository {

    val config: StateFlow<AppConfiguration?>
    fun saveConfig(config:AppConfiguration)

    fun getConfig(): AppConfiguration?
}