package blblblbl.simplelife.configtimer.data.persistent_storage.utils


import blblblbl.simplelife.configtimer.data.di.ConfigFeature
import blblblbl.simplelife.configtimer.data.model.Config
import javax.inject.Inject

class StorageConverterImpl @Inject constructor(
) : StorageConverter {
    @Inject
    @ConfigFeature
    lateinit var jsonParser: JsonParser
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