package blblblbl.simplelife.configuration.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.configuration.presentation.ConfigurationFragmentViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun ConfigurationFragment(
){
    val viewModel: ConfigurationFragmentViewModel = hiltViewModel()

}