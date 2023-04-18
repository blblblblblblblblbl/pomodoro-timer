package blblblbl.simplelife.history.data.repository

import android.util.Log
import blblblbl.simplelife.history.data.datasource.HistoryDataSource
import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.domain.repository.HistoryRepository
import java.sql.Date
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource
):HistoryRepository {
    override suspend fun getDayInfo(date: LocalDate): DayInfo? =
        historyDataSource.getDayInfo(date)
    /*override suspend fun getDayInfo(date: LocalDate): DayInfo? {
        Log.d("MyLog","date:${date.toString()}")
        //date.toString()=2023-04-07 year/month/day
        val rand = Random.nextInt(0,3)
        when(rand){
            0->return null
            1-> return DayInfo(
                Date(2022,15,15),
                totalRelaxTime = 500,
                totalWorkTime = 500,
                goal = 500,
                progress = 600
            )
            2-> return DayInfo(
                Date(2022,15,15),
                totalRelaxTime = 500,
                totalWorkTime = 500,
                goal = 700,
                progress = 600
            )
            else -> return null
        }

    }*/
}