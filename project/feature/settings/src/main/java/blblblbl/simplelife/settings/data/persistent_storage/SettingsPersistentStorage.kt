package blblblbl.simplelife.settings.data.persistent_storage

import blblblbl.simplelife.settings.data.model.AppConfiguration

interface SettingsPersistentStorage {
    fun addProperty(key: String?, value: String?)

    fun clear()

    fun getProperty(key: String?): String?

    fun addConfig(config: AppConfiguration)

    fun getConfig(): AppConfiguration?

    companion object {
        const val APP_CONFIG_KEY = "app_config_key"
    }
}