package blblblbl.simplelife.history.ui.detailed

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.ui.detailed.util.toHhMmSs
import kotlin.math.ceil

@Composable
fun DetailedDayScreen(
    dayInfo: DayInfo
){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = dayInfo.date.toString(), modifier = Modifier.align(CenterHorizontally), style = MaterialTheme.typography.headlineLarge)
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Total work time", style = MaterialTheme.typography.headlineLarge)
                Text(text = dayInfo.totalWorkTime.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Total relax time", style = MaterialTheme.typography.headlineLarge)
                Text(text = dayInfo.totalRelaxTime.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "goal ", style = MaterialTheme.typography.headlineLarge)
                Text(text = dayInfo.goal.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "progress ", style = MaterialTheme.typography.headlineLarge)
                Text(text = dayInfo.progress.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
            }
        }
    }

}

