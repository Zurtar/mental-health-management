package com.zurtar.mhma.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshoe.charty.line.model.LineData
import com.zurtar.mhma.data.BiWeeklyMoodRepository
import com.zurtar.mhma.data.DailyEvaluationEntry
import com.zurtar.mhma.data.DailyMoodRepository
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * Represents the UI state for the mood calendar, used for displaying mood-related data.
 * @property foo A placeholder string value.
 */
data class MoodCalendarUIState(
    val foo: String
)

/**
 * Represents the UI state for daily mood analytics.
 * This includes historical evaluations and the currently selected date.
 * @property pastEvaluations A list of past daily mood evaluations.
 * @property selectedDate The date currently selected by the user.
 */
data class DailyAnalyticsUIState(
    val pastEvaluations: List<DailyEvaluationEntry> = listOf(),
    val selectedDate: LocalDate? = null
)

/**
 * ViewModel responsible for managing daily mood analytics.
 * It handles data retrieval, state management, and user interactions.
 *
 * @property dailyMoodRepository Repository used to fetch and observe mood entries.
 */
@HiltViewModel
data class DailyAnalyticsViewModel @Inject constructor(
    private val dailyMoodRepository: DailyMoodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DailyAnalyticsUIState())
    val uiState: StateFlow<DailyAnalyticsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Fetch and update initial mood entries
            _uiState.update { c ->
                c.copy(
                    pastEvaluations = dailyMoodRepository.fetchMoodEntries()
                )
            }

            // Observe mood entries and update UI state
            dailyMoodRepository.getMoodEntries().collect { entries ->
                _uiState.update { currentState ->
                    currentState.copy(
                        pastEvaluations = entries
                    )
                }
            }
        }
    }

    /**
     * Updates the UI state with the selected date.
     * @param localDate The date selected by the user.
     */
    fun selectDate(localDate: LocalDate?) {
        _uiState.update { currentState -> currentState.copy(selectedDate = localDate) }
    }
}

/**
 * Represents the UI state for BiWeekly mood analytics.
 * This includes past BiWeekly evaluations and corresponding graph data.
 * @property pastEvaluations A list of past BiWeekly mood evaluations.
 * @property graphData A list of data points representing mood trends over time.
 */
data class BiWeeklyAnalyticsUIState(
    val pastEvaluations: List<BiWeeklyEvaluationEntry>? = null,
    val graphData: List<LineData>? = null
)

/**
 * ViewModel responsible for managing BiWeekly mood analytics.
 * It fetches BiWeekly mood data, processes it, and provides it to the UI.
 *
 * @property biWeeklyMoodRepository Repository used to fetch BiWeekly mood entries.
 */
@HiltViewModel
class BiWeeklyAnalyticsViewModel @Inject constructor(
    private val biWeeklyMoodRepository: BiWeeklyMoodRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BiWeeklyAnalyticsUIState())
    val uiState: StateFlow<BiWeeklyAnalyticsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val moodList = biWeeklyMoodRepository.fetchLatestMoodEntries().toMutableList()

                val data = moodList.mapNotNull { currentEntry ->
                    if (currentEntry.dateCompleted == null)
                        null
                    else {
                        LineData(
                            yValue = currentEntry.depressionScore.toFloat(),
                            xValue = currentEntry.dateCompleted!!
                        )
                    }
                }
                currentState.copy(
                    pastEvaluations = moodList,
                    graphData = data
                )
            }
        }
    }
}
