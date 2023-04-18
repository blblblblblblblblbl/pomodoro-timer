package blblblbl.simplelife.history.domain.repository

import blblblbl.simplelife.history.domain.model.DayInfo
import java.time.LocalDate

interface HistoryRepository {
    suspend fun getDayInfo(date: LocalDate):DayInfo?
}