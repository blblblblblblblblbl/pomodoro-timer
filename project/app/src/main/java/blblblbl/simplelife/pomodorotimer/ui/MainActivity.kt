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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
fun AppScreen(startDestination:String = "timer_screen"){
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
    startDestination:String = "MainFeedNested"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = "timer_screen") {
            TimerFragment()
        }
    }
}