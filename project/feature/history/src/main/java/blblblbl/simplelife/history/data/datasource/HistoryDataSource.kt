package blblblbl.simplelife.history.data.datasource

import blblblbl.simplelife.history.data.model.DayInfo
import java.time.LocalDate

interface HistoryDataSource {
    suspend fun getDayInfo(date: LocalDate): DayInfo?
}