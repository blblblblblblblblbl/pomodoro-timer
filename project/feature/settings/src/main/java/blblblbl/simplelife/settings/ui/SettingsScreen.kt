package blblblbl.simplelife.settings.ui


import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import blblblbl.simplelife.settings.domain.model.AppConfiguration
import blblblbl.simplelife.settings.domain.model.ThemeMode
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun SettingsScreen(
    config:AppConfiguration?,
    saveConfig:(AppConfiguration)->Unit
) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        AlarmPicker(
            config?.alarmRingtone,
            setAlarm = {alarm->
                config?.let { config->
                    saveConfig(config.copy(alarmRingtone = alarm))
                }
            }
        )
        ThemePicker(
            saveColor = {color->
                if (config!=null){
                    saveConfig(config.copy(themeColor = color))
                }
            },
            setMode = {mode->
                if (config!=null){
                    saveConfig(config.copy(themeMode = mode))
                }
            }
        )
        NextStagePicker(
            initial = config?.isAutomaticNextStage?:false,
            setState = {bool->
                config?.let { config->
                    saveConfig(config.copy(isAutomaticNextStage = bool))
                }
            }
        )
    }
}

@Composable
fun AlarmPicker(
    initialAlarm:String?,
    setAlarm:(String)->Unit
) {
    var initialAlarm = initialAlarm
    val context = LocalContext.current
    val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }
    DropDownCard(
        modifier = Modifier
            .padding(20.dp)
            .heightIn(max = 300.dp),
        header = "alarm"
    ) {
        val alarms = mutableListOf<String>()
        var pickedAlarm by remember { mutableStateOf(initialAlarm)}
        var mpIsPLaying by remember { mutableStateOf(mediaPlayer.isPlaying) }
        for (i in 1..6) {
            alarms.add("android.resource://"+"blblblbl.simplelife.pomodorotimer"+"/raw/ringtone$i")
            //also possible variant
            //val pkgName: String = context.getPackageName()//blblblbl.simplelife.pomodorotimer
            //val path = Uri.parse("android.resource://$pkgName" + "/" + blblblbl.simplelife.recources.R.raw.ringtone2)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {

                items(alarms) { alarm ->
                    val alarmName = alarm.removePrefix("android.resource://blblblbl.simplelife.pomodorotimer/raw/")
                    Button(
                        onClick = {
                            pickedAlarm = alarm
                            mediaPlayer.apply {
                                reset()
                                setDataSource(context, Uri.parse(alarm))
                                prepare()
                                start()
                                mpIsPLaying = true
                            }
                            Toast.makeText(context, "$alarmName played", Toast.LENGTH_SHORT).show()
                        },

                        colors =
                        if (alarm == initialAlarm)
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.outline,
                                if (MaterialTheme.colorScheme.outline.luminance()>0.5) Color.Black else Color.White
                            )
                        else
                            ButtonDefaults.buttonColors(),
                        shape = CircleShape,
                        modifier =
                        if (alarm==pickedAlarm) Modifier
                            .fillMaxWidth()
                            .border(
                                4.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                        else Modifier.fillMaxWidth()
                    ) {
                        Text(text = alarmName)
                    }
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        if (mpIsPLaying){
                            IconButton(onClick = {
                                mediaPlayer.pause()
                                mpIsPLaying = false
                            }) {
                                Icon(Icons.Default.Pause, contentDescription = "stop alarm")
                            }
                        }
                        if ((pickedAlarm!=null) && (pickedAlarm!=initialAlarm)){
                            Button(onClick = {
                                setAlarm(pickedAlarm!!)
                                initialAlarm = pickedAlarm
                            }
                            ) {
                                Text(text = "set alarm")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemePicker(
    saveColor:(Int)->Unit,
    setMode:(ThemeMode)->Unit
) {
    val context = LocalContext.current
    DropDownCard(
        modifier = Modifier
            .padding(20.dp)
            .heightIn(max = 300.dp),
        header = "theme"
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            ThemeColorPicker(saveColor)
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline
            )
            LightDarkThemePicker(setMode)
        }
    }
}
@Composable
fun ThemeColorPicker(
    saveColor:(Int)->Unit
){
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ColorPickerDialog(
            setShowDialog = { b -> showDialog = b },
            changeColor = {color->
                saveColor(color)
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
    LazyVerticalGrid(
        columns = GridCells.Adaptive(50.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(colors) { color ->
            IconButton(
                onClick =
                {
                    saveColor(color.toArgb())
                    Toast.makeText(context, "color saved", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .size(48.dp)
            ) {

                Surface(
                    color = color,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        ),
                    shape = CircleShape
                ) {}
            }
        }
        item {
            val colorStops = arrayOf(
                0.0f to Color.Yellow,
                0.2f to Color.Red,
                1f to Color.Blue
            )
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                        .background(
                            Brush.horizontalGradient(colorStops = colorStops),
                            shape = CircleShape
                        ),
                    shape = CircleShape
                ) {}
            }
        }
    }
}
@Composable
fun LightDarkThemePicker(
    setMode:(ThemeMode)->Unit
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "light/dark")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = {setMode(ThemeMode.AUTO)}
            ) {
                Icon(Icons.Default.BrightnessAuto, contentDescription = "AutoMode", modifier = Modifier.size(48.dp))
            }
            IconButton(
                onClick = {setMode(ThemeMode.LIGHT)}
            ) {
                Icon(Icons.Default.LightMode, contentDescription = "LightMode", modifier = Modifier.size(48.dp))
            }
            IconButton(
                onClick = {setMode(ThemeMode.NIGHT)}
            ) {
                Icon(Icons.Default.ModeNight, contentDescription = "ModeNight", modifier = Modifier.size(48.dp))
            }
        }
    }
}
@Composable
fun NextStagePicker(
    initial:Boolean,
    setState:(Boolean)->Unit
) {
    Card() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "timer go to next stage")
            Row(verticalAlignment = Alignment.CenterVertically) {
                var checked by remember { mutableStateOf(initial?:false) }
                Text(text = "manual")
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = !checked
                        setState(checked)
                    })
                Text(text = "auto")
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