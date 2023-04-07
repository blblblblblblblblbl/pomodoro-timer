package blblblbl.simplelife.configtimer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.configtimer.domain.model.Config
import blblblbl.simplelife.configtimer.presentation.ConfigurationFragmentViewModel

@Composable
fun ConfigurationFragment(
){
    val viewModel: ConfigurationFragmentViewModel = hiltViewModel()
    ConfigurationScreen(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        initialConfiguration = Config(5,10,10),
        saveOnCLick = {}
    )
}