package blblblbl.simplelife.configuration.data.persistent_storage.utils


import blblblbl.simplelife.configuration.data.model.Config
import javax.inject.Inject

class StorageConverterImpl @Inject constructor(
    private val jsonParser: JsonParser
) : StorageConverter {
    override fun configToJson(config: Config): String? {
        return jsonParser.toJson(
            config,
            Config::class.java
        )
    }

    override fun configFromJson(json: String): Config? {
        return jsonParser.fromJson<Config>(
            json,
            Config::class.java
        )
    }
}