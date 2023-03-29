package blblblbl.simplelife.configuration.domain.repository

import blblblbl.simplelife.configuration.domain.model.Config

interface ConfigurationRepository {
    suspend fun saveConfiguration(config: Config)
    suspend fun getConfiguration(): Config
}