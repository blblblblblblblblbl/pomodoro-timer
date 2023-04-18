package blblblbl.simplelife.history.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.history.domain.usecase.DayInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(
    private val dayInfoUseCase: DayInfoUseCase
): ViewModel() {
    /*fun getDayColor(date: LocalDate):Color{
        viewModelScope.launch(Dispatchers.IO) {
            val info = dayInfoUseCase.getInfo(date)
            if (info==null) return Color.Transparent
        }
    }*/
    suspend fun getDayColor(date: LocalDate):Color{
        val info = dayInfoUseCase.getInfo(date)
        if (info==null) return Color.Transparent
        else{
            if (info.progress>=info.goal) return Color.Green
            else return Color.Yellow
        }
    }
}