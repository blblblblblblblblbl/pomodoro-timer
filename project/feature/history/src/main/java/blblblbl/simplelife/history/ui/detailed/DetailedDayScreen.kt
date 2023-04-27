package blblblbl.simplelife.history.ui.detailed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.ui.detailed.util.toHhMmSs
import kotlin.math.ceil

@Composable
fun DetailedDayScreen(
    dayInfo: DayInfo,
    editDayInfo: (DayInfo)->Unit
){
    var workTime by remember { mutableStateOf(dayInfo.totalWorkTime?:0) }
    var relaxTime by remember { mutableStateOf(dayInfo.totalRelaxTime?:0) }
    var goal by remember { mutableStateOf(dayInfo.goal?:0) }
    var progress by remember { mutableStateOf(dayInfo.progress?:0) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = dayInfo.date.toString(), modifier = Modifier.align(CenterHorizontally), style = MaterialTheme.typography.headlineLarge)
            DropDownCard(
                header = {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Total work time", style = MaterialTheme.typography.headlineLarge)
                        Text(text = dayInfo.totalWorkTime.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TimePicker(
                            initialTime = (workTime/1000).toInt(),
                            onTimeChange = {time->
                                workTime = (time*1000).toLong()
                            }
                        )
                        Button(onClick = {
                            editDayInfo(
                                DayInfo(
                                    date = dayInfo.date,
                                    totalWorkTime = workTime,
                                    totalRelaxTime = relaxTime,
                                    goal = goal,
                                    progress = progress
                                )
                            )
                        }) {
                            Text(text = "EDIT", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            )
            DropDownCard(
                header = {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Total relax time", style = MaterialTheme.typography.headlineLarge)
                        Text(text = dayInfo.totalRelaxTime.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TimePicker(
                            initialTime = (relaxTime/1000).toInt(),
                            onTimeChange = {time->
                                relaxTime = (time*1000).toLong()
                            }
                        )
                        Button(onClick = {
                            editDayInfo(
                                DayInfo(
                                    date = dayInfo.date,
                                    totalWorkTime = workTime,
                                    totalRelaxTime = relaxTime,
                                    goal = goal,
                                    progress = progress
                                )
                            )
                        }) {
                            Text(text = "EDIT", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            )
            DropDownCard(
                header = {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "goal ", style = MaterialTheme.typography.headlineLarge)
                        Text(text = dayInfo.goal.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TimePicker(
                            initialTime = (goal/1000).toInt(),
                            onTimeChange = {time->
                                goal = (time*1000).toLong()
                            }
                        )
                        Button(onClick = {
                            editDayInfo(
                                DayInfo(
                                    date = dayInfo.date,
                                    totalWorkTime = workTime,
                                    totalRelaxTime = relaxTime,
                                    goal = goal,
                                    progress = progress
                                )
                            )
                        }) {
                            Text(text = "EDIT", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            )
            DropDownCard(
                header = {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "progress ", style = MaterialTheme.typography.headlineLarge)
                        Text(text = dayInfo.progress.toHhMmSs(), style = MaterialTheme.typography.headlineLarge)
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TimePicker(
                            initialTime = (progress/1000).toInt(),
                            onTimeChange = {time->
                                progress = (time*1000).toLong()
                            }
                        )
                        Button(onClick = {
                            editDayInfo(
                                DayInfo(
                                    date = dayInfo.date,
                                    totalWorkTime = workTime,
                                    totalRelaxTime = relaxTime,
                                    goal = goal,
                                    progress = progress
                                )
                            )
                        }) {
                            Text(text = "EDIT", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            )

        }
    }

}

@Composable
fun DropDownCard(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column {
            header()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1F))
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "expand")
                }
            }
            AnimatedVisibility(expanded) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            AnimatedVisibility(expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    content()
                }
            }

        }

    }
}

