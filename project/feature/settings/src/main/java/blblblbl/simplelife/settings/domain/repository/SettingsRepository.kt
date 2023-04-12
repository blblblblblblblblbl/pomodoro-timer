package blblblbl.simplelife.settings.domain.repository

import blblblbl.simplelife.settings.domain.model.AppConfiguration


interface SettingsRepository {

    fun saveConfig(config:AppConfiguration)

    fun getConfig(): AppConfiguration?
}