package blblblbl.simplelife.configuration.data.repository

import blblblbl.simplelife.configuration.data.persistent_storage.PersistentStorage
import blblblbl.simplelife.configuration.data.utils.mapToData
import blblblbl.simplelife.configuration.data.utils.mapToDomain
import blblblbl.simplelife.configuration.domain.model.Config
import blblblbl.simplelife.configuration.domain.repository.ConfigurationRepository
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
):ConfigurationRepository {
    override suspend fun saveConfiguration(config: Config) =
        persistentStorage.addConfig(config.mapToData())

    override suspend fun getConfiguration(): Config =
        persistentStorage.getConfig()?.mapToDomain()?: Config(null,null,null)
}