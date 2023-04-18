package blblblbl.simplelife.history.data.utils

import blblblbl.simplelife.history.data.model.DayInfo as DataDayInfo
import blblblbl.simplelife.history.domain.model.DayInfo as DomainDayInfo

fun DataDayInfo.mapToDomain():DomainDayInfo{
    val domainConfig:DomainDayInfo =
        DomainDayInfo(
            this.date,
            this.totalWorkTime,
            this.totalRelaxTime,
            this.goal,
            this.progress
        )
    return domainConfig
}
fun DomainDayInfo.mapToData():DataDayInfo{
    val dataConfig:DataDayInfo =
        DataDayInfo(
            this.date,
            this.totalWorkTime,
            this.totalRelaxTime,
            this.goal,
            this.progress
        )
    return dataConfig
}