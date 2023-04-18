package blblblbl.simplelife.history.data.database

import blblblbl.simplelife.history.domain.model.DayInfo
import java.time.LocalDate

interface DatabaseHistory {
    suspend fun getDayInfo(date: LocalDate): DayInfo?
}