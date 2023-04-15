package blblblbl.simplelife.settings.data.model

import blblblbl.simplelife.settings.domain.model.ThemeMode

data class AppConfiguration(
    val themeColor:Int?,
    val themeMode: ThemeMode?,
    val alarmRingtone:String?,
    val isAutomaticNextStage:Boolean?
)
