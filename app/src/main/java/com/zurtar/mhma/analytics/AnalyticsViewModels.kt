package com.zurtar.mhma.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.data.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MoodCalendarUIState(
    val foo: String
)


data class MoodGraphUIState(
   //val graphData: List<LineData >
)

//@HiltViewModel
class MoodCalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoodCalendarUIState(""))
    val uiState: StateFlow<MoodCalendarUIState> = _uiState.asStateFlow()


}

data class BiWeeklyAnalyticsUIState(
    val pastEvaluations: List<BiWeeklyEvaluationEntry>? = null
)

@HiltViewModel
class BiWeeklyAnalyticsViewModel @Inject constructor(
    private val moodRepository: MoodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BiWeeklyAnalyticsUIState())
    val uiState: StateFlow<BiWeeklyAnalyticsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    pastEvaluations = moodRepository.fetchLatestMoodEntries().toMutableList()
                )
            }
        }
    }


}