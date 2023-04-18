package blblblbl.simplelife.timer.data.database


import blblblbl.simplelife.timer.data.model.DayInfo
import java.sql.Date

interface DatabaseHistory {
    suspend fun saveDayInfo(dayInfo: DayInfo)
    suspend fun getDayInfo(date: Date): DayInfo?
}