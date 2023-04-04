package blblblbl.simplelife.timer.domain.repository

import blblblbl.simplelife.timer.domain.model.Config

interface ConfigurationRepository {
    suspend fun saveConfiguration(config: Config)
    suspend fun getConfiguration(): Config
}