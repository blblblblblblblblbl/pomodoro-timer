package blblblbl.simplelife.history.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.history.presentation.HistoryFragmentViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryFragment(
    dayOnClick:(LocalDate)->Unit
){
    val viewModel: HistoryFragmentViewModel = hiltViewModel()
    HistoryScreen(
        dayCheck = { date->viewModel.getDayColor(date) },
        dayOnClick = dayOnClick
    )
}