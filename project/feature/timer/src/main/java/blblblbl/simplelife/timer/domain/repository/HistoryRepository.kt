package blblblbl.simplelife.timer.domain.repository

import blblblbl.simplelife.timer.domain.model.DayInfo
import java.util.*

interface HistoryRepository {
    suspend fun saveDayInfo(dayInfo: DayInfo)
}