package com.zurtar.mhma.analytics

import androidx.lifecycle.ViewModel
import com.zurtar.mhma.mood.DailyEvaluationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class MoodCalendarUIState(
    val foo: String
)

class MoodCalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoodCalendarUIState(""))
    val uiState: StateFlow<MoodCalendarUIState> = _uiState.asStateFlow()

}