package blblblbl.simplelife.configtimer.data.utils

import blblblbl.simplelife.configtimer.data.model.Config as DataConfig
import blblblbl.simplelife.configtimer.domain.model.Config as DomainConfig

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