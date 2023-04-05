package blblblbl.simplelife.timer.ui.alarm

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel()
}