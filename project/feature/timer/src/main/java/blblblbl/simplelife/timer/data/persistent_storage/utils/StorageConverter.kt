package blblblbl.simplelife.timer.data.persistent_storage.utils

import blblblbl.simplelife.timer.data.model.Config


interface StorageConverter {

    fun configToJson(config: Config): String?

    fun configFromJson(json: String): Config?
}