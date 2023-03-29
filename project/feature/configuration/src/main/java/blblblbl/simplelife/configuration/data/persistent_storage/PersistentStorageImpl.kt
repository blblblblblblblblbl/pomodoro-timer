package blblblbl.simplelife.configuration.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.configuration.data.model.Config
import blblblbl.simplelife.configuration.data.persistent_storage.utils.StorageConverter
import javax.inject.Inject

class PersistentStorageImpl @Inject constructor(
    private val storageConverter: StorageConverter,
    private val sharedPreferences: SharedPreferences
) :PersistentStorage {
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

    override fun addConfig(config: Config) {
        val value = storageConverter.configToJson(config)
        editor.putString(PersistentStorage.CONFIG_KEY, value)
        editor.apply()
    }

    override fun getConfig(): Config? {
        val json = sharedPreferences.getString(PersistentStorage.CONFIG_KEY, null)
        val config = storageConverter.configFromJson(json ?: "")
        return config
    }
}