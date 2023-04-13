package blblblbl.simplelife.settings.data.repository

import android.util.Log
import blblblbl.simplelife.settings.data.persistent_storage.SettingsPersistentStorage
import blblblbl.simplelife.settings.data.utils.mapToData
import blblblbl.simplelife.settings.data.utils.mapToDomain
import blblblbl.simplelife.settings.domain.model.AppConfiguration
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsPersistentStorage: SettingsPersistentStorage
):SettingsRepository {
    private val _config = MutableStateFlow<AppConfiguration?>(getConfig())
    override val config = _config.asStateFlow()
    override fun saveConfig(config: AppConfiguration) {
        settingsPersistentStorage.addConfig(config.mapToData())
        Log.d("MyLog","SettingsRepositoryImpl config save:${config.toString()}")
        _config.value = config
    }


    override fun getConfig(): AppConfiguration? {
        val configuration = settingsPersistentStorage.getConfig()?.mapToDomain()?:AppConfiguration(null,null,false)
        //_config.value = configuration
        return configuration
    }
}