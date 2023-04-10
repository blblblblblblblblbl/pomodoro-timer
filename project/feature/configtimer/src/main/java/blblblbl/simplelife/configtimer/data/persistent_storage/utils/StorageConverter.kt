package blblblbl.simplelife.configtimer.data.persistent_storage.utils

import blblblbl.simplelife.configtimer.data.model.Config


interface StorageConverter {

    fun configToJson(config: Config): String?

    fun configFromJson(json: String): Config?
}