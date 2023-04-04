package blblblbl.simplelife.timer.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.timer.data.model.Config
import blblblbl.simplelife.timer.data.persistent_storage.utils.StorageConverter
import javax.inject.Inject

class ConfigurationPersistentStorageImpl @Inject constructor(
    private val storageConverter: StorageConverter,
    private val sharedPreferences: SharedPreferences
) :ConfigurationPersistentStorage {
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


    override fun getConfig(): Config? {
        val json = sharedPreferences.getString(ConfigurationPersistentStorage.CONFIG_KEY, null)
        val config = storageConverter.configFromJson(json ?: "")
        return config
    }
}