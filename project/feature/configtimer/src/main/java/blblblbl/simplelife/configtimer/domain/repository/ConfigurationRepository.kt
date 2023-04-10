package blblblbl.simplelife.configtimer.domain.repository

import blblblbl.simplelife.configtimer.domain.model.Config

interface ConfigurationRepository {
    suspend fun saveConfiguration(config: Config)
    suspend fun getConfiguration(): Config?
}