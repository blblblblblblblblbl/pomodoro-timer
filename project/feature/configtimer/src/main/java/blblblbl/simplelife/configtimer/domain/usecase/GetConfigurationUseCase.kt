package blblblbl.simplelife.configtimer.domain.usecase

import blblblbl.simplelife.configtimer.domain.model.Config
import blblblbl.simplelife.configtimer.domain.repository.ConfigurationRepository
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val repository: ConfigurationRepository
) {
    suspend fun execute(): Config? =
        repository.getConfiguration()
}