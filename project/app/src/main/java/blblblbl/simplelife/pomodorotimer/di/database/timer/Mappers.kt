package blblblbl.simplelife.pomodorotimer.di.database.timer

import java.sql.Date
import blblblbl.simplelife.database.model.DayEntity as DBDay
import blblblbl.simplelife.timer.data.model.DayInfo as TimerDay

fun DBDay?.mapToTimer():TimerDay?{
    if (this==null) return null
    else{
        val timerDay: TimerDay =
            TimerDay(
                date = Date.valueOf(this.date),
                totalWorkTime = this.totalWorkTime,
                totalRelaxTime = this.totalRelaxTime,
                goal = this.goal,
                progress = this.progress
            )
        return timerDay
    }
}

fun TimerDay?.mapToDB():DBDay?{
    if (this==null) return null
    else{
        val dBDay: DBDay =
            DBDay(
                date = this.date.toString(),
                totalWorkTime = this.totalWorkTime,
                totalRelaxTime = this.totalRelaxTime,
                goal = this.goal,
                progress = this.progress
            )
        return dBDay
    }
}