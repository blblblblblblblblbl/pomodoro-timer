package blblblbl.simplelife.settings.ui

import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.settings.presentation.SettingsFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SettingsFragment(){
    val viewModel:SettingsFragmentViewModel = hiltViewModel()
    viewModel.getConfig()
    val config by viewModel.config.collectAsState()
    SettingsScreen(
        config = config,
        saveConfig = {conf->
            viewModel.saveConfig(conf)
        }
    )
}