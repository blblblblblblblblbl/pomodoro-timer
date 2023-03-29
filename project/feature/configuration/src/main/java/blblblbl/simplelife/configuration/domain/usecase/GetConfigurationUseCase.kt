package blblblbl.simplelife.configuration.domain.usecase

import blblblbl.simplelife.configuration.domain.model.Config
import blblblbl.simplelife.configuration.domain.repository.ConfigurationRepository
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val repository: ConfigurationRepository
) {
    suspend fun execute(): Config =
        repository.getConfiguration()
}