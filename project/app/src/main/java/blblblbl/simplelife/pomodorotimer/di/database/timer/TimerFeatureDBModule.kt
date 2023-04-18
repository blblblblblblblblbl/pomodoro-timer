package blblblbl.simplelife.pomodorotimer.di.database.timer


import blblblbl.simplelife.pomodorotimer.di.database.DataBaseCreator
import blblblbl.simplelife.pomodorotimer.di.database.history.mapToHistory
import blblblbl.simplelife.timer.data.database.DatabaseHistory
import blblblbl.simplelife.timer.data.model.DayInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.sql.Date
import java.time.LocalDate


@Module
@InstallIn(SingletonComponent::class)
class TimerFeatureDBModule{

    @Provides
    fun provideSearchDB(dbCreator: DataBaseCreator): DatabaseHistory =
        object : DatabaseHistory {
            val db = dbCreator.getDB()


            override suspend fun saveDayInfo(dayInfo: DayInfo) {
                dayInfo.mapToDB()?.let { db.historyDao().insert(dayInfo = it) }
            }

            override suspend fun getDayInfo(date: Date): DayInfo? {
                val dayEntity = db.historyDao().getDayInfoByBin(date.toString())
                return dayEntity.mapToTimer()
            }

        }
}