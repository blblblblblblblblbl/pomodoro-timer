package blblblbl.simplelife.pomodorotimer.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val route: String
}
object TimerDest:AppDestination{
    override val route: String = "timerDest"
}
object ConfigTimerDest:AppDestination{
    override val route: String = "configTimerDest"
}
object HistoryDest:AppDestination{
    override val route: String = "historyDest"
}
object AppSettingDest:AppDestination{
    override val route: String = "appSettingDest"
}
object OnBoardingDest:AppDestination{
    override val route: String = "onBoardingDest"
}