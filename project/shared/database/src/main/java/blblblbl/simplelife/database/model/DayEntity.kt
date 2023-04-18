package blblblbl.simplelife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DaysTable")
data class DayEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "totalWorkTime")
    val totalWorkTime:Long,
    @ColumnInfo(name = "totalRelaxTime")
    val totalRelaxTime:Long,
    @ColumnInfo(name = "goal")
    val goal:Long,
    @ColumnInfo(name = "progress")
    val progress:Long
)
