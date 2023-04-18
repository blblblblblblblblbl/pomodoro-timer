package blblblbl.simplelife.timer.data.utils

import blblblbl.simplelife.timer.data.model.Config as DataConfig
import blblblbl.simplelife.timer.domain.model.Config as DomainConfig

import blblblbl.simplelife.timer.data.model.DayInfo as DataDayInfo
import blblblbl.simplelife.timer.domain.model.DayInfo as DomainDayInfo

fun DataConfig.mapToDomain():DomainConfig{
    val domainConfig:DomainConfig =
        DomainConfig(
            this.workTime,
            this.relaxTime,
            this.goal
        )
    return domainConfig
}
fun DomainConfig.mapToData():DataConfig{
    val dataConfig:DataConfig =
        DataConfig(
            this.workTime,
            this.relaxTime,
            this.goal
        )
    return dataConfig
}


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