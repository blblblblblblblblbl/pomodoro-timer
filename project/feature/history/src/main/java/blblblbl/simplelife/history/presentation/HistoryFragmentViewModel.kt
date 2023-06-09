package blblblbl.simplelife.history.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.history.domain.model.DayInfo
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

    suspend fun getDayColor(date: LocalDate):DayInfo? =
        dayInfoUseCase.getInfo(date)
}