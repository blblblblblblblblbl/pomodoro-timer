package blblblbl.simplelife.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import blblblbl.simplelife.database.dao.HistoryDao
import blblblbl.simplelife.database.model.DayEntity


@Database(entities = [DayEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}