package com.zurtar.mhma.analytics

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.auth.AuthState
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.data.BiWeeklyMoodRepository
import com.zurtar.mhma.data.DailyEvaluationEntry
import com.zurtar.mhma.data.DailyMoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject


data class MoodCalendarUIState(
    val foo: String
)

//@HiltViewModel
class MoodCalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoodCalendarUIState(""))
    val uiState: StateFlow<MoodCalendarUIState> = _uiState.asStateFlow()


}

data class DailyAnalyticsUIState(
    val pastEvaluations: List<DailyEvaluationEntry> = listOf(),
    val selectedDate: LocalDate? = null
)


@HiltViewModel
data class DailyAnalyticsViewModel @Inject constructor(
    private val dailyMoodRepository: DailyMoodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DailyAnalyticsUIState())
    val uiState: StateFlow<DailyAnalyticsUIState> = _uiState.asStateFlow()

    init {


        viewModelScope.launch {
            _uiState.update { c ->
                c.copy(
                    pastEvaluations = dailyMoodRepository.fetchMoodEntries()
                )
            }


            dailyMoodRepository.getMoodEntries().collect { entries ->
                _uiState.update { currentState ->
                    currentState.copy(
                        pastEvaluations = entries
                    )
                }
            }
        }
    }

    fun selectDate(localDate: LocalDate?) {
        _uiState.update { currentState -> currentState.copy(selectedDate = localDate) }
    }
}

data class BiWeeklyAnalyticsUIState(
    val pastEvaluations: List<BiWeeklyEvaluationEntry>? = null
)

@HiltViewModel
class BiWeeklyAnalyticsViewModel @Inject constructor(
    private val biWeeklyMoodRepository: BiWeeklyMoodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BiWeeklyAnalyticsUIState())
    val uiState: StateFlow<BiWeeklyAnalyticsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    pastEvaluations = biWeeklyMoodRepository.fetchLatestMoodEntries()
                        .toMutableList()
                )
            }
        }
    }


}