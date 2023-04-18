package blblblbl.simplelife.pomodorotimer.navigation.graphs

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import blblblbl.simplelife.history.ui.detailed.DetailedDayFragment
import blblblbl.simplelife.history.ui.calendar.HistoryFragment
import blblblbl.simplelife.pomodorotimer.navigation.HistoryDest
import java.time.LocalDate

fun NavGraphBuilder.historyGraph(navController: NavHostController) {
    navigation(startDestination = HistoryDest.route, route = "HistoryNested") {
        composable(route = HistoryDest.route) {
            HistoryFragment(
                dayOnClick = { date->
                    navController.navigate("${HistoryNavGraph.DETAILED_ROUTE}/${date}")
                }
            )
        }
        composable(
            route =  "${HistoryNavGraph.DETAILED_ROUTE}/{${HistoryNavGraph.DETAILED_DAY_KEY}}",
            arguments = listOf(
                navArgument(name = HistoryNavGraph.DETAILED_DAY_KEY){
                    type = NavType.StringType
                }
            )
        ){navBackStackEntry ->

            val dateString = navBackStackEntry.arguments?.getString(HistoryNavGraph.DETAILED_DAY_KEY)
            dateString?.let {dateString->
                Log.d("MyLog", "dateString?.let {dateString->$dateString")
                val date = LocalDate.parse(dateString)
                DetailedDayFragment(date = date)
            }
        }
    }
}
private object HistoryNavGraph{
    const val DETAILED_ROUTE = "HistoryNavGraph.DetailedRoute"
    const val DETAILED_DAY_KEY = "HistoryNavGraph.DetailedBinKey"
}