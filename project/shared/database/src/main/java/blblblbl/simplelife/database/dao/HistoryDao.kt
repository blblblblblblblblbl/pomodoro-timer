package blblblbl.simplelife.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import blblblbl.simplelife.database.model.DayEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM DaysTable WHERE date LIKE :date")
    fun getDayInfoByBin(date:String): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dayInfo: DayEntity)

    @Query("DELETE FROM DaysTable")
    suspend fun clear()
}