package blblblbl.simplelife.settings.data.persistent_storage.utils



import blblblbl.simplelife.settings.di.SettingsFeature
import blblblbl.simplelife.settings.data.model.AppConfiguration
import javax.inject.Inject

class StorageConverterImpl @Inject constructor() : StorageConverter
{
    @Inject
    @SettingsFeature
    lateinit var jsonParser: JsonParser
    override fun configToJson(config: AppConfiguration): String? {
        return jsonParser.toJson(
            config,
            AppConfiguration::class.java
        )
    }

    override fun configFromJson(json: String): AppConfiguration? {
        return jsonParser.fromJson<AppConfiguration>(
            json,
            AppConfiguration::class.java
        )
    }
}