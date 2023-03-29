package blblblbl.simplelife.configuration.domain.usecase

import blblblbl.simplelife.configuration.domain.model.Config
import blblblbl.simplelife.configuration.domain.repository.ConfigurationRepository
import javax.inject.Inject

class SaveConfigurationUseCase @Inject constructor(
    private val repository: ConfigurationRepository
) {
    suspend fun execute(config: Config) =
        repository.saveConfiguration(config)
}