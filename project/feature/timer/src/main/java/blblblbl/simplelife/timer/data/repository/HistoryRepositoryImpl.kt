package blblblbl.simplelife.timer.data.repository

import blblblbl.simplelife.timer.data.database.DatabaseHistory
import blblblbl.simplelife.timer.data.utils.mapToData
import blblblbl.simplelife.timer.domain.model.DayInfo
import blblblbl.simplelife.timer.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val databaseHistory: DatabaseHistory
):HistoryRepository {
    override suspend fun saveDayInfo(dayInfo: DayInfo) {
        databaseHistory.saveDayInfo(dayInfo.mapToData())
    }

}