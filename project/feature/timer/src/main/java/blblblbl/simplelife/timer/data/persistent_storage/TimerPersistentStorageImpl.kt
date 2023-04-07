package blblblbl.simplelife.timer.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.timer.domain.model.TimerStage
import blblblbl.simplelife.timer.domain.model.TimerState
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimerPersistentStorageImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val sharedPref:SharedPreferences
):TimerPersistentStorage {
    //private var sharedPref : SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    private var timerCounting = false
    private var isTimerInPause = false
    private var startTime: Date? = null
    private var stopTime: Date? = null
    private var pauseTime:Date? = null
    private var timerState:TimerState? = null
    private var timerStage: TimerStage? = null

    init
    {
        timerCounting = sharedPref.getBoolean(COUNTING_KEY, false)

        val startString = sharedPref.getString(START_TIME_KEY, null)
        if (startString != null)
            startTime = dateFormat.parse(startString)

        val stopString = sharedPref.getString(STOP_TIME_KEY, null)
        if (stopString != null)
            stopTime = dateFormat.parse(stopString)

        val pauseString = sharedPref.getString(PAUSE_TIME_KEY, null)
        if (pauseString != null)
            pauseTime = dateFormat.parse(pauseString)

        val timerStatePref = sharedPref.getString(STATE_KEY,null)
        timerState = if (timerStatePref != null)
            TimerState.valueOf(timerStatePref)
        else TimerState.STOP

        val timerStagePref = sharedPref.getString(STAGE_KEY,null)
        timerStage = if (timerStagePref != null)
            TimerStage.valueOf(timerStagePref)
        else TimerStage.WORK
    }


    override fun startTime(): Date? = startTime

    override fun setStartTime(date: Date?)
    {
        startTime = date
        with(sharedPref.edit())
        {
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(START_TIME_KEY,stringDate)
            apply()
        }
    }

    override fun stopTime(): Date? = stopTime

    override fun setStopTime(date: Date?)
    {
        stopTime = date
        Log.d("MyLog", "stopTime:$stopTime")
        with(sharedPref.edit())
        {
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(STOP_TIME_KEY,stringDate)
            apply()
        }
    }

    override fun timerCounting(): Boolean = timerCounting

    override fun isTimerInPause(): Boolean = timerCounting

    override fun setTimerInPause(value: Boolean) { isTimerInPause = value }

    override fun pauseTime(): Date? = pauseTime

    override fun setPauseTime(date: Date?)
    {
        pauseTime = date
        Log.d("MyLog", "pauseTime:$pauseTime")
        with(sharedPref.edit())
        {
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(PAUSE_TIME_KEY,stringDate)
            apply()
        }
    }

    override fun timerState(): TimerState? = timerState

    override fun setTimerState(timerState: TimerState?)
    {
        this.timerState = timerState
        with(sharedPref.edit())
        {
            val stringSate = timerState.toString()
            Log.d("MyLog", "stringSate:$stringSate")
            putString(STATE_KEY,stringSate)
            apply()
        }
    }

    override fun timerStage(): TimerStage? = timerStage

    override fun setTimerStage(timerStage: TimerStage?)
    {
        this.timerStage = timerStage
        with(sharedPref.edit())
        {
            val stringStage = timerStage.toString()
            Log.d("MyLog", "stringSate:$stringStage")
            putString(STAGE_KEY,stringStage)
            apply()
        }
    }
    override fun setTimerCounting(value: Boolean)
    {
        timerCounting = value
        with(sharedPref.edit())
        {
            putBoolean(COUNTING_KEY,value)
            apply()
        }
    }

    companion object
    {
        const val PREFERENCES = "prefs"
        const val START_TIME_KEY = "startKey"
        const val STOP_TIME_KEY = "stopKey"
        const val PAUSE_TIME_KEY = "pauseKey"
        const val COUNTING_KEY = "countingKey"
        const val STATE_KEY = "timerstatekey"
        const val STAGE_KEY = "timerstagekey"
    }
}