package blblblbl.simplelife.pomodorotimer.di.database.history

import java.sql.Date
import blblblbl.simplelife.database.model.DayEntity as DBDay
import blblblbl.simplelife.history.data.model.DayInfo as HistoryDay

fun DBDay?.mapToHistory():HistoryDay?{
    if (this==null) return null
    else{
        val historyDay: HistoryDay =
            HistoryDay(
                date = Date.valueOf(this.date),
                totalWorkTime = this.totalWorkTime,
                totalRelaxTime = this.totalRelaxTime,
                goal = this.goal,
                progress = this.progress
            )
        return historyDay
    }
}

fun HistoryDay?.mapToDB():DBDay?{
    if (this==null) return null
    else{
        val dBDay: DBDay =
            blblblbl.simplelife.database.model.DayEntity(
                date = this.date.toString(),
                totalWorkTime = this.totalWorkTime,
                totalRelaxTime = this.totalRelaxTime,
                goal = this.goal,
                progress = this.progress
            )
        return dBDay
    }
}