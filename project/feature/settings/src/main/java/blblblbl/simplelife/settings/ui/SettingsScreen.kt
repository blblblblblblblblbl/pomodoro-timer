package blblblbl.simplelife.settings.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun SettingsScreen() {
    ColorPicker(
        changeColor = {
        },
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    )
}

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    changeColor: (Int) -> Unit
) {
    val controller = rememberColorPickerController()
    var color by remember{ mutableStateOf<Color>(Color.White) }
    Box(modifier = modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                modifier = Modifier.requiredHeight(100.dp).border(2.dp,Color.Black,MaterialTheme.shapes.extraLarge).fillMaxWidth()
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
            Button(onClick = { changeColor(color.toArgb()) }) {
                Text(text = "set color")
            }
        }
    }

}