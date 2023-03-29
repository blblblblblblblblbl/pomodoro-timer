package blblblbl.simplelife.configuration.data.persistent_storage

import blblblbl.simplelife.configuration.data.model.Config

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