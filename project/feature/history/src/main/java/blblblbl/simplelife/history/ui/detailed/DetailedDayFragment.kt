package blblblbl.simplelife.history.ui.detailed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.history.presentation.DetailedDayFragmentViewModel
import java.time.LocalDate

@Composable
fun DetailedDayFragment(date: LocalDate){
    val viewModel :DetailedDayFragmentViewModel = hiltViewModel()
    viewModel.getDayInfo(date)
    val dayInfo by viewModel.dayInfo.collectAsState()
    dayInfo?.let { dayInfo ->
        DetailedDayScreen(dayInfo = dayInfo)
    }

}