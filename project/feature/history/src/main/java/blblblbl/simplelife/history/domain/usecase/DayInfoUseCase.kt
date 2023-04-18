package blblblbl.simplelife.history.domain.usecase

import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.domain.repository.HistoryRepository
import java.time.LocalDate
import javax.inject.Inject

class DayInfoUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend fun getInfo(date: LocalDate):DayInfo? =
        repository.getDayInfo(date)
}