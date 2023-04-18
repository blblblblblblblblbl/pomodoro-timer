package blblblbl.simplelife.timer.domain.usecase

import blblblbl.simplelife.timer.data.di.TimerFeature
import blblblbl.simplelife.timer.domain.model.DayInfo
import blblblbl.simplelife.timer.domain.repository.HistoryRepository
import java.sql.Date
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    @TimerFeature private val historyRepository: HistoryRepository
) {
    suspend fun saveDayInfo(dayInfo: DayInfo) =
        historyRepository.saveDayInfo(dayInfo)
    suspend fun getDayInfo(date: Date): DayInfo? =
        historyRepository.getDayInfo(date)
}