package blblblbl.simplelife.configtimer.data.repository

import blblblbl.simplelife.configtimer.data.persistent_storage.PersistentStorage
import blblblbl.simplelife.configtimer.data.utils.mapToData
import blblblbl.simplelife.configtimer.data.utils.mapToDomain
import blblblbl.simplelife.configtimer.domain.model.Config
import blblblbl.simplelife.configtimer.domain.repository.ConfigurationRepository
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
): ConfigurationRepository {
    override suspend fun saveConfiguration(config: Config) =
        persistentStorage.addConfig(config.mapToData())

    override suspend fun getConfiguration(): Config? =
        persistentStorage.getConfig()?.mapToDomain()
}