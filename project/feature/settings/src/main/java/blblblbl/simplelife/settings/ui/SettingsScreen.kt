package blblblbl.simplelife.settings.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        /*DropDownCard(
            modifier = Modifier
                .padding(20.dp)
                .heightIn(max = 300.dp),
            header = "alarm"
        ) {
            val alarms = mutableListOf<String>()
            for (i in 1..15) {
                alarms.add("alarm $i")
            }
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(alarms) { alarm ->
                    Button(
                        onClick = { Toast.makeText(context, "$alarm played", Toast.LENGTH_SHORT).show() },
                        modifier = Modifier.fillMaxWidth()) {
                        Text(text = alarm)
                    }
                }
            }
        }
        DropDownCard(
            modifier = Modifier
                .padding(20.dp)
                .heightIn(max = 300.dp),
            header = "theme"
        ) {
            var showDialog by remember { mutableStateOf(false) }
            if (showDialog) {
                ColorPickerDialog(
                    setShowDialog = { b -> showDialog = b },
                    changeColor ={
                        Toast.makeText(context,"color saved",Toast.LENGTH_SHORT).show()
                    })
            }
            val colors = mutableListOf<Color>(
                Color.Transparent,
                Color.White,
                Color.Black,
                Color.Blue,
                Color.Gray,
                Color.Green,
                Color.Magenta,
                Color.Red,
                Color.Yellow
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(48.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    items(colors) { color ->
                        IconButton(onClick = { *//*TODO*//* }) {
                            Surface(
                                color = color,
                                modifier = Modifier
                                    .size(48.dp)
                                    .border(
                                        1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                shape = RoundedCornerShape(8.dp)
                            ) {

                            }
                        }
                    }
                }
                Button(onClick = { showDialog = true }) {
                    Text(text = "custom")
                }
            }
        }*/
        AlarmPicker()
        ThemePicker()
        NextStagePicker()

    }

    /*CustomColorPicker(
        changeColor = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )*/
}

@Composable
fun AlarmPicker() {
    val context = LocalContext.current
    DropDownCard(
        modifier = Modifier
            .padding(20.dp)
            .heightIn(max = 300.dp),
        header = "alarm"
    ) {
        val alarms = mutableListOf<String>()
        for (i in 1..15) {
            alarms.add("alarm $i")
        }
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(alarms) { alarm ->
                Button(
                    onClick = {
                        Toast.makeText(context, "$alarm played", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = alarm)
                }
            }
        }
    }
}

@Composable
fun ThemePicker() {
    val context = LocalContext.current
    DropDownCard(
        modifier = Modifier
            .padding(20.dp)
            .heightIn(max = 300.dp),
        header = "theme"
    ) {
        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            ColorPickerDialog(
                setShowDialog = { b -> showDialog = b },
                changeColor = {
                    Toast.makeText(context, "color saved", Toast.LENGTH_SHORT).show()
                })
        }
        val colors = mutableListOf<Color>(
            Color.Transparent,
            Color.White,
            Color.Black,
            Color.Blue,
            Color.Gray,
            Color.Green,
            Color.Magenta,
            Color.Red,
            Color.Yellow
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(48.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                items(colors) { color ->
                    IconButton(onClick = { /*TODO*/ }) {
                        Surface(
                            color = color,
                            modifier = Modifier
                                .size(48.dp)
                                .border(
                                    1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            shape = RoundedCornerShape(8.dp)
                        ) {

                        }
                    }
                }
            }
            Button(onClick = { showDialog = true }) {
                Text(text = "custom")
            }
        }
    }
}
@Composable
fun NextStagePicker(){
    Card() {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(15.dp)) {
            Text(text = "timer go to next stage")
            Row(verticalAlignment = Alignment.CenterVertically) {
                var checked by remember { mutableStateOf(false) }
                Text(text = "auto")
                Switch(checked = checked, onCheckedChange = {checked = !checked})
                Text(text = "manually")
            }
        }
    }
}
@Composable
fun ColorPickerDialog(
    setShowDialog: (Boolean) -> Unit,
    changeColor: (Int) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            CustomColorPicker(
                changeColor = changeColor,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun CustomColorPicker(
    modifier: Modifier = Modifier,
    changeColor: (Int) -> Unit
) {
    val controller = rememberColorPickerController()
    var color by remember { mutableStateOf<Color>(Color.White) }
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                modifier = Modifier
                    .requiredHeight(100.dp)
                    .border(2.dp, Color.Black, MaterialTheme.shapes.extraLarge)
                    .fillMaxWidth()
            ) {}
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                }
            )
            Button(onClick = {
                changeColor(color.toArgb())
            }) {
                Text(text = "set color")
            }
        }
    }
}

@Composable
fun DropDownCard(
    modifier: Modifier = Modifier,
    header: String = "",
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = header)
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
                        .fillMaxHeight()
                ) {
                    content()
                }
            }

        }

    }
}