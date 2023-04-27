package blblblbl.simplelife.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.history.domain.model.DayInfo
import blblblbl.simplelife.history.domain.usecase.DayInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailedDayFragmentViewModel @Inject constructor(
    private val dayInfoUseCase: DayInfoUseCase
):ViewModel() {

    private val _dayInfo = MutableStateFlow<DayInfo?>(null)
    val dayInfo = _dayInfo.asStateFlow()

    fun getDayInfo(date: LocalDate){
        viewModelScope.launch(Dispatchers.IO) {
            val reponse = dayInfoUseCase.getInfo(date)
            _dayInfo.value = reponse?:DayInfo(
                date= Date.valueOf(date.toString()),
                totalWorkTime = 0,
                totalRelaxTime = 0,
                goal = 0,
                progress = 0
            )
        }
    }
    fun saveDayInfo(dayInfo: DayInfo){
        _dayInfo.value = dayInfo
        viewModelScope.launch(Dispatchers.IO){
            dayInfoUseCase.saveDayInfo(dayInfo)
        }
    }
}