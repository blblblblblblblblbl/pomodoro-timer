package blblblbl.simplelife.timer.domain.repository

import blblblbl.simplelife.timer.domain.model.DayInfo
import java.sql.Date
import java.util.*

interface HistoryRepository {
    suspend fun saveDayInfo(dayInfo: DayInfo)
    suspend fun getDayInfo(date: Date): DayInfo?
}