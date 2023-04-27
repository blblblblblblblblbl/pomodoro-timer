package blblblbl.simplelife.history.data.database


import blblblbl.simplelife.history.data.model.DayInfo
import java.time.LocalDate

interface DatabaseHistory {
    suspend fun saveDayInfo(dayInfo: DayInfo)
    suspend fun getDayInfo(date: LocalDate): DayInfo?
}