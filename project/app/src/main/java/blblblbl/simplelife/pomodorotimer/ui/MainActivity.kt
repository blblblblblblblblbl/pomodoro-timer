package blblblbl.simplelife.pomodorotimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import blblblbl.simplelife.configtimer.ui.ConfigurationFragment
import blblblbl.simplelife.pomodorotimer.navigation.*
import blblblbl.simplelife.pomodorotimer.ui.theming.AppTheme
import blblblbl.simplelife.settings.ui.SettingsFragment
import blblblbl.simplelife.timer.ui.TimerFragment
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(startDestination: AppDestination = TimerDest){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    Surface() {
        ModalNavigationDrawer(
            drawerContent =
            {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 50.dp)
                ) {
                    DrawerContent(
                        navController = navController,
                        drawerState = drawerState
                    )
                }

            },
            gesturesEnabled = false,
            drawerState = drawerState
        ) {
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                openMenu = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }

}
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination:AppDestination = TimerDest,
    openMenu: ()->Unit
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
            menuOnClick = openMenu)
        }
        composable(route = ConfigTimerDest.route) {
            ConfigurationFragment()
        }
        composable(route = HistoryDest.route) {
            Text(text = "HistoryDest")
        }
        composable(route = AppSettingDest.route) {
            SettingsFragment()
        }
        composable(route = OnBoardingDest.route) {
            Text(text = "OnBoardingDest")
        }
        composable(route = AuthorsDest.route) {
            Text(text = "AuthorsDest")
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