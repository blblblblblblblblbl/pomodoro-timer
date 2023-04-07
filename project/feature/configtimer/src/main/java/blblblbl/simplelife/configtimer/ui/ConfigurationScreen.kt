package blblblbl.simplelife.configtimer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.configtimer.domain.model.Config
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun ConfigurationScreen(
    modifier: Modifier = Modifier,
    initialConfiguration: Config,
    saveOnCLick:(Config)->Unit
) {
    var workTime by remember { mutableStateOf(initialConfiguration.workTime?:0) }
    var relaxTime by remember { mutableStateOf(initialConfiguration.relaxTime?:0) }
    var goal by remember { mutableStateOf(initialConfiguration.goal?:0) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "work", style = MaterialTheme.typography.headlineLarge)
                TimePicker(
                    initialTime = workTime,
                    onTimeChange = {time->
                        workTime = time
                    }
                )
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "relax", style = MaterialTheme.typography.headlineLarge)
                TimePicker(
                    initialTime = relaxTime,
                    onTimeChange = {time->
                        relaxTime = time
                    }
                )
            }
        }
        Card() {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(text = "goal", style = MaterialTheme.typography.headlineLarge)
                NumberPicker(
                    value = goal,
                    onValueChange =
                    {
                        goal = it
                    },
                    range = 0..99
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                saveOnCLick(Config(workTime, relaxTime, goal))
            }
        ) {
            Text(text = "save", style = MaterialTheme.typography.bodyLarge)
        }
    }

}

@Composable
fun TimePicker(
    initialTime: Int = 0,//in seconds
    onTimeChange: (Int) -> Unit
) {
    var hours by remember { mutableStateOf(initialTime / 3600) }
    var minutes by remember { mutableStateOf(initialTime%3600/60) }
    var seconds by remember { mutableStateOf(initialTime % 60) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "hh", style = MaterialTheme.typography.headlineSmall)
        NumberPicker(
            value = hours,
            onValueChange =
            {
                hours = it
                onTimeChange(timeInSecs(hours,minutes,seconds))
            },
            range = 0..23
        )
        Text(text = "mm", style = MaterialTheme.typography.headlineSmall)
        NumberPicker(
            value = minutes,
            onValueChange =
            {
                minutes = it
                onTimeChange(timeInSecs(hours,minutes,seconds))
            },
            range = 0..59
        )
        Text(text = "ss", style = MaterialTheme.typography.headlineSmall)
        NumberPicker(
            value = seconds,
            onValueChange =
            {
                seconds = it
                onTimeChange(timeInSecs(hours,minutes,seconds))
            },
            range = 0..59
        )
    }
}

fun timeInSecs(hh: Int, mm: Int, ss: Int): Int =
    hh * 3600 + mm * 60 + ss

@Preview(
    showBackground = true
)
@Composable
fun TimePickerPreview(){
    TimePicker(
        initialTime = timeInSecs(5,6,7),
        onTimeChange = {})
}

@Preview(
    showBackground = true
)
@Composable
fun ConfigurationScreenPreview(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            ConfigurationScreen(
                initialConfiguration = Config(
                    timeInSecs(5,6,7),
                    timeInSecs(1,2,3),
                    5
                ),
                saveOnCLick = {}
            )
        }
    }
}