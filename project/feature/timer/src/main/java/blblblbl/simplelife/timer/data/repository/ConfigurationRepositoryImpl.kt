package blblblbl.simplelife.timer.data.repository

import blblblbl.simplelife.timer.data.persistent_storage.PersistentStorage
import blblblbl.simplelife.timer.data.utils.mapToData
import blblblbl.simplelife.timer.data.utils.mapToDomain
import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.repository.ConfigurationRepository
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
):ConfigurationRepository {
    override suspend fun saveConfiguration(config: Config) =
        persistentStorage.addConfig(config.mapToData())

    override suspend fun getConfiguration(): Config =
        persistentStorage.getConfig()?.mapToDomain()?: Config(null,null,null)
}