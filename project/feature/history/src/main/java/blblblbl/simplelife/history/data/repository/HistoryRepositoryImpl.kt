package blblblbl.simplelife.history.data.repository

import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.domain.repository.HistoryRepository
import java.sql.Date
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

class HistoryRepositoryImpl @Inject constructor(

):HistoryRepository {
    override suspend fun getDayInfo(date: LocalDate): DayInfo? {
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

    }
}