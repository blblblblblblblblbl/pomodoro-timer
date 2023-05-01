package blblblbl.simplelife.configtimer.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.configtimer.domain.model.Config
import blblblbl.simplelife.configtimer.presentation.ConfigurationFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ConfigurationFragment(
    saveOnClick:()->Unit
) {
    val viewModel: ConfigurationFragmentViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    viewModel.getConfig()
    val config by viewModel.savedConfig.collectAsState()
    ConfigurationScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        initialConfiguration = config ?: Config(0, 0, 0),
        saveOnCLick = { config ->
            if (!(config.workTime == 0 || config.relaxTime == 0 || config.goal == 0)) {
                coroutineScope.launch{
                    val job = viewModel.saveConfig(config)
                    job.join()
                    saveOnClick()
                }
                Toast.makeText(context,"parameters saved",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "parameter can't be 0", Toast.LENGTH_SHORT).show()
            }
        }
    )
}