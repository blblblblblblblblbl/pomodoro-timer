package blblblbl.simplelife.timer.data.persistent_storage

import blblblbl.simplelife.timer.data.model.Config

interface PersistentStorage {
    fun addProperty(key: String?, value: String?)

    fun clear()

    fun getProperty(key: String?): String?

    fun addConfig(config: Config)

    fun getConfig(): Config?

    companion object {
        const val CONFIG_KEY = "config_key"
    }
}