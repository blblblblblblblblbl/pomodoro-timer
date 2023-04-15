package blblblbl.simplelife.settings.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.settings.di.SettingsFeature
import blblblbl.simplelife.settings.data.model.AppConfiguration
import blblblbl.simplelife.settings.data.persistent_storage.utils.StorageConverter
import javax.inject.Inject

class SettingsPersistentStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
):SettingsPersistentStorage {
    @Inject
    @SettingsFeature
    lateinit var storageConverter: StorageConverter

    private val editor = sharedPreferences.edit()
    override fun addProperty(name: String?, value: String?) {

        Log.d("MyLog", "addProperty:$name $value")
        editor.putString(name, value)
        editor.apply()
    }

    override fun clear() {
        editor.clear()
        editor.apply()
    }

    override fun getProperty(name: String?): String? {
        return sharedPreferences.getString(name, null)
    }

    override fun addConfig(config: AppConfiguration) {
        val value = storageConverter.configToJson(config)
        editor.putString(SettingsPersistentStorage.APP_CONFIG_KEY, value)
        editor.apply()
    }

    override fun getConfig(): AppConfiguration? {
        val json = sharedPreferences.getString(SettingsPersistentStorage.APP_CONFIG_KEY, null)
        val config = storageConverter.configFromJson(json ?: "")
        return config
    }
}