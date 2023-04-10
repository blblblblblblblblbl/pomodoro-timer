package blblblbl.simplelife.pomodorotimer.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val name:String
    val route: String
}
object TimerDest:AppDestination{
    override val name: String = "timer"
    override val route: String = "timerDest"
}
object ConfigTimerDest:AppDestination{
    override val name: String = "timer configuration"
    override val route: String = "configTimerDest"
}
object HistoryDest:AppDestination{
    override val name: String = "history"
    override val route: String = "historyDest"
}
object AppSettingDest:AppDestination{
    override val name: String = "settings"
    override val route: String = "appSettingDest"
}
object OnBoardingDest:AppDestination{
    override val name: String = "onboarding"
    override val route: String = "onBoardingDest"
}
object AuthorsDest:AppDestination{
    override val name: String = "authors"
    override val route: String = "authorsDest"
}
val menuDestinations = listOf<AppDestination>(TimerDest,HistoryDest,AppSettingDest,OnBoardingDest,AuthorsDest)
