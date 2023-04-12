package blblblbl.simplelife.settings.data.utils

import blblblbl.simplelife.settings.data.model.AppConfiguration as DataConfig
import blblblbl.simplelife.settings.domain.model.AppConfiguration as DomainConfig

fun DataConfig.mapToDomain():DomainConfig{
    val domainConfig:DomainConfig =
        DomainConfig(
            this.themeColor,
            this.alarmRingtone,
            this.isAutomaticNextStage
        )
    return domainConfig
}
fun DomainConfig.mapToData():DataConfig{
    val dataConfig:DataConfig =
        DataConfig(
            this.themeColor,
            this.alarmRingtone,
            this.isAutomaticNextStage
        )
    return dataConfig
}