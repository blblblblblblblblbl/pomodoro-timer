package blblblbl.simplelife.configtimer.data.persistent_storage

import blblblbl.simplelife.configtimer.data.model.Config

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