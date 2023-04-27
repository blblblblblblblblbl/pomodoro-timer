package blblblbl.simplelife.pomodorotimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import blblblbl.simplelife.configtimer.ui.ConfigurationFragment
import blblblbl.simplelife.onboarding.OnBoardingScreen
import blblblbl.simplelife.onboarding.ShowOnBoarding
import blblblbl.simplelife.pomodorotimer.di.SharedPrefsModule
import blblblbl.simplelife.pomodorotimer.navigation.*
import blblblbl.simplelife.pomodorotimer.navigation.graphs.historyGraph
import blblblbl.simplelife.pomodorotimer.presentation.MainActivityViewModel
import blblblbl.simplelife.pomodorotimer.ui.theming.AppTheme
import blblblbl.simplelife.settings.ui.SettingsFragment
import blblblbl.simplelife.timer.ui.TimerFragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    @Inject
    lateinit var showOnBoarding: ShowOnBoarding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            AppTheme(
                configFlow = viewModel.getSettingsFlow()
            ) {
                val useDarkIcons = !isSystemInDarkTheme()
                val color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
                SideEffect {
                    systemUiController.setSystemBarsColor(color, darkIcons = useDarkIcons)
                }

                AppScreen(
                    startDestination = if ((this::showOnBoarding.isInitialized)&&!showOnBoarding.IsShown()) OnBoardingDest else TimerDest,
                    onBoardingOnClick= {showOnBoarding.saveShown()}

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    startDestination: AppDestination = TimerDest,
    onBoardingOnClick:()->Unit = {}
){
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
            gesturesEnabled = true,
            drawerState = drawerState
        ) {
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                openMenu = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onBoardingOnClick = onBoardingOnClick
            )
        }
    }

}
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination:AppDestination = TimerDest,
    openMenu: ()->Unit,
    onBoardingOnClick:()->Unit = {}
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
        historyGraph(navController)
        composable(route = AppSettingDest.route) {
            SettingsFragment()
        }
        composable(route = OnBoardingDest.route) {
            OnBoardingScreen(
                startOnClick = {
                    navController.navigateSingleTopTo(TimerDest.route)
                    onBoardingOnClick()
                }
            )
        }
        composable(route = AuthorsDest.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Authors", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Kirill Tolmachev", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Varvara Sapozhnikova", style = MaterialTheme.typography.headlineLarge)
                }
            }
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