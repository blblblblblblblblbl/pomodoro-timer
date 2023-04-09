package blblblbl.simplelife.pomodorotimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import blblblbl.simplelife.configtimer.ui.ConfigurationFragment
import blblblbl.simplelife.pomodorotimer.navigation.AppDestination
import blblblbl.simplelife.pomodorotimer.navigation.ConfigTimerDest
import blblblbl.simplelife.pomodorotimer.navigation.TimerDest
import blblblbl.simplelife.pomodorotimer.ui.theming.AppTheme
import blblblbl.simplelife.timer.ui.TimerFragment
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            AppTheme {
                val useDarkIcons = !isSystemInDarkTheme()
                val color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
                SideEffect {
                    systemUiController.setSystemBarsColor(color, darkIcons = useDarkIcons)
                }
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen(startDestination: AppDestination = TimerDest){
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        startDestination = startDestination
    )
}
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination:AppDestination = TimerDest
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(route = TimerDest.route) {
            TimerFragment(onSettingsClicked = {
                navController.navigateSingleTopTo(ConfigTimerDest.route)
            },
            menuOnClick = {})
        }
        composable(route = ConfigTimerDest.route) {
            ConfigurationFragment()
        }
    }
}
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }