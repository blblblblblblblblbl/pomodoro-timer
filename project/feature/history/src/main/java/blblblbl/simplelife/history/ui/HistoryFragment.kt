package blblblbl.simplelife.history.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.history.presentation.HistoryFragmentViewModel
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryFragment(){
    val viewModel: HistoryFragmentViewModel = hiltViewModel()
    HistoryScreen(
        dayCheck = {date->
            val rand = Random.nextInt(0,2)
            when(rand){
                0->Color.Transparent
                1->Color.Yellow
                2->Color.Green
                else -> {Color.Transparent}
            }
        },
        dayOnClick = {}
    )
}