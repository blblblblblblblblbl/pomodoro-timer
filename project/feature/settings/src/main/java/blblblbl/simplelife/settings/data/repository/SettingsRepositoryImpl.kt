package blblblbl.simplelife.settings.data.repository

import blblblbl.simplelife.settings.data.persistent_storage.SettingsPersistentStorage
import blblblbl.simplelife.settings.data.utils.mapToData
import blblblbl.simplelife.settings.data.utils.mapToDomain
import blblblbl.simplelife.settings.domain.model.AppConfiguration
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsPersistentStorage: SettingsPersistentStorage
):SettingsRepository {
    override fun saveConfig(config: AppConfiguration) =
        settingsPersistentStorage.addConfig(config.mapToData())

    override fun getConfig(): AppConfiguration? =
        settingsPersistentStorage.getConfig()?.mapToDomain()?:AppConfiguration(null,null,false)
}