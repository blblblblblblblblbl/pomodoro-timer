package blblblbl.simplelife.configuration.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.configuration.presentation.ConfigurationFragmentViewModel

@Composable
fun ConfigurationFragment(){
    val viewModel: ConfigurationFragmentViewModel = hiltViewModel()
}