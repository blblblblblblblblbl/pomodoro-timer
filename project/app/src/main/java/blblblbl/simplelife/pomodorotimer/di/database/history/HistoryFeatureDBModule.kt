package blblblbl.simplelife.pomodorotimer.di.database.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import blblblbl.simplelife.history.data.database.DatabaseHistory
import blblblbl.simplelife.history.data.model.DayInfo
import blblblbl.simplelife.pomodorotimer.di.database.DataBaseCreator
import blblblbl.simplelife.pomodorotimer.di.database.timer.mapToDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import java.time.LocalDate


@Module
@InstallIn(SingletonComponent::class)
class HistoryFeatureDBModule{

    @Provides
    fun provideSearchDB(dbCreator: DataBaseCreator): DatabaseHistory =
        object : DatabaseHistory {
            val db = dbCreator.getDB()
            override suspend fun saveDayInfo(dayInfo: DayInfo) {
                dayInfo.mapToDB()?.let { db.historyDao().insert(dayInfo = it) }
            }


            override suspend fun getDayInfo(date: LocalDate): DayInfo? {
                val dayEntity = db.historyDao().getDayInfoByBin(date.toString())
                return dayEntity.mapToHistory()
            }

        }
}