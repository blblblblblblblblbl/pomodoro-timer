package blblblbl.simplelife.timer.domain.usecase

import blblblbl.simplelife.timer.domain.model.Config
import blblblbl.simplelife.timer.domain.repository.TimerRepository
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    suspend fun execute(): Config =
        repository.getConfiguration()
}