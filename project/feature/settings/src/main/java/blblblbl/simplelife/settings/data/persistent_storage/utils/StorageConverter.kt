package blblblbl.simplelife.settings.data.persistent_storage.utils

import blblblbl.simplelife.settings.data.model.AppConfiguration


interface StorageConverter {

    fun configToJson(config: AppConfiguration): String?

    fun configFromJson(json: String): AppConfiguration?
}