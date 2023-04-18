package blblblbl.simplelife.timer.data.database


import blblblbl.simplelife.timer.data.model.DayInfo
import java.util.*

interface DatabaseHistory {
    suspend fun saveDayInfo(dayInfo: DayInfo)
}