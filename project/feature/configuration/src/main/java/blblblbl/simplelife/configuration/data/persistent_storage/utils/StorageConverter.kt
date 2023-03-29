package blblblbl.simplelife.configuration.data.persistent_storage.utils

import blblblbl.simplelife.configuration.data.model.Config


interface StorageConverter {

    fun configToJson(config: Config): String?

    fun configFromJson(json: String): Config?
}