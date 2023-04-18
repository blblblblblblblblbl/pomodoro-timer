package blblblbl.simplelife.history.data.datasource

import blblblbl.simplelife.history.data.database.DatabaseHistory
import blblblbl.simplelife.history.domain.model.DayInfo
import java.time.LocalDate
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val databaseHistory: DatabaseHistory
):HistoryDataSource {
    override suspend fun getDayInfo(date: LocalDate): DayInfo? =
        databaseHistory.getDayInfo(date)
}