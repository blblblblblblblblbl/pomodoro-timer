package blblblbl.simplelife.pomodorotimer.di.database

import android.content.Context
import androidx.room.Room
import blblblbl.simplelife.database.HistoryDatabase
import blblblbl.simplelife.database.model.DayEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseCreator@Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val db = Room.databaseBuilder(
        appContext,
        HistoryDatabase::class.java,
        "BinDB"
    ).build()
    /*init {
        runBlocking {
            db.historyDao().insert(
                DayEntity(
                    "2023-04-18",
                    totalRelaxTime = 500,
                    totalWorkTime = 500,
                    goal = 500,
                    progress = 600
                )
            )
        }
    }*/
    fun getDB(): HistoryDatabase = db
}