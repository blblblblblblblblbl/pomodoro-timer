package blblblbl.simplelife.history.domain.model

import java.sql.Date

data class DayInfo(
    val date:Date,
    val totalWorkTime:Long,
    val totalRelaxTime:Long,
    val goal:Long,
    val progress:Long
)
