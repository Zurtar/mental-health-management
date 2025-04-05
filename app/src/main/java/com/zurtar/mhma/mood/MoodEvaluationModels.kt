package com.zurtar.mhma.mood

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.analytics.toDate
import com.zurtar.mhma.analytics.toLocalDate
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.data.BiWeeklyMoodRepository
import com.zurtar.mhma.data.DailyEvaluationEntry
import com.zurtar.mhma.data.DailyMoodRepository
import com.zurtar.mhma.theme.EmojiFrown
import com.zurtar.mhma.theme.EmojiNeutral
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.time.LocalDate
import javax.inject.Inject

/**
 * UI state for Daily Evaluation.
 *
 * @property dailyEntry The current [DailyEvaluationEntry] for the user.
 * @property isSubmitted Flag indicating if the daily entry has been submitted.
 * @property page The current page of the evaluation.
 */
data class DailyEvaluationUiState(
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(),
    val isSubmitted: Int = 0,
    val page: Int = 0
)

/**
 * ViewModel for handling daily mood evaluation operations.
 *
 * This ViewModel manages the state of the daily evaluation, including submitting the evaluation,
 * navigating through the pages, and updating the selected emotions and their intensities.
 *
 * @property dailyMoodRepository Repository for handling daily mood data storage and retrieval.
 */
@HiltViewModel
class DailyEvaluationViewModel @Inject constructor(
    private val dailyMoodRepository: DailyMoodRepository
) : ViewModel() {

    // Mutable state flow for daily evaluation UI state
    private val _uiState = MutableStateFlow(DailyEvaluationUiState())
    // Publicly exposed state flow for UI to observe
    val uiState: StateFlow<DailyEvaluationUiState> = _uiState.asStateFlow()

    /**
     * Submits the current daily evaluation by recording the emotions and their intensities.
     *
     * This method updates the UI state to reflect the submission and saves the daily mood entry
     * into the repository.
     */
    fun onSubmit() {
        val emotionsMap: Map<String, Float> =
            _uiState.value.dailyEntry.selectedEmotions.zip(_uiState.value.dailyEntry.emotionIntensities)
                .sortedByDescending { (_, intensity) -> intensity }
                .toMap()

        _uiState.update { currentState ->
            currentState.copy(
                isSubmitted = 1,
                dailyEntry = currentState.dailyEntry.copy(
                    emotionsMap = emotionsMap,
                    dateCompleted = LocalDate.now().toDate(),
                    strongestEmotion = emotionsMap.entries.first().toPair()
                )
            )
        }

        viewModelScope.launch {
            dailyMoodRepository.addMoodEntry(
                _uiState.value.dailyEntry.copy(
                    dateCompleted = Date.from(Instant.now())
                )
            )
        }
    }

    /**
     * Navigates to the next page of the daily evaluation.
     */
    fun onNext() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page + 1)
        }
    }

    /**
     * Navigates to the previous page of the daily evaluation.
     */
    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

    /**
     * Updates the stress level based on the selected emoji.
     *
     * @param emoji The emoji representing the user's current stress level.
     */
    fun updateEmotion(emoji: ImageVector) {
        val stressLevel = when (emoji) {
            EmojiFrown -> "Very Stressed"
            EmojiNeutral -> "Mildly Stressed"
            else -> "Not Stressed"
        }
        _uiState.update { currentState ->
            currentState.copy(
                dailyEntry = currentState.dailyEntry.copy(
                    stressLevel = stressLevel
                )
            )
        }
    }

    /**
     * Toggles the selection of an emotion for the daily evaluation.
     *
     * @param emotion The emotion to be selected or deselected.
     */
    fun emotionSelect(emotion: String) {
        Log.println(Log.DEBUG, "DailyEval:: ", "$emotion")

        val emotionList = _uiState.value.dailyEntry.selectedEmotions.toMutableList()

        if (emotionList.contains(emotion))
            emotionList.remove(emotion)
        else if (emotionList.size < 3) {
            emotionList.add(emotion)
        }
        Log.println(Log.DEBUG, "DailyEval:: ", "$emotionList")

        _uiState.update { currentState ->
            val d_entry = DailyEvaluationEntry(
                selectedEmotions = emotionList,
                emotionIntensities = currentState.dailyEntry.emotionIntensities,
                stressLevel = currentState.dailyEntry.stressLevel,
                dateCompleted = currentState.dailyEntry.dateCompleted
            )

            currentState.copy(dailyEntry = d_entry)
        }
    }

    /**
     * Updates the intensity of a selected emotion.
     *
     * @param value The new intensity value for the emotion.
     * @param index The index of the emotion to be updated.
     */
    fun updateIntensity(value: Float, index: Int) {
        val intensityList = _uiState.value.dailyEntry.emotionIntensities.toMutableList()
        intensityList[index] = value
        Log.println(Log.DEBUG, "DailyEval:: ", "$value")

        val emotionsMap: Map<String, Float> =
            _uiState.value.dailyEntry.selectedEmotions.zip(intensityList)
                .sortedByDescending { (_, intensity) -> intensity }
                .toMap()

        _uiState.update { currentState ->
            currentState.copy(
                dailyEntry = currentState.dailyEntry.copy(
                    emotionIntensities = intensityList,
                    emotionsMap = emotionsMap
                )
            )
        }
        Log.println(Log.DEBUG, "DailyEval:: ", "$intensityList")
    }

    /**
     * Updates the strongest emotion based on the current selection and intensity.
     */
    fun updateStrongestEmotion() {
        val strongest = _uiState.value.dailyEntry.emotionsMap.entries.first().toPair()

        _uiState.update { currentState ->
            currentState.copy(
                dailyEntry = currentState.dailyEntry.copy(
                    strongestEmotion = strongest
                )
            )
        }
    }
}

/**
 * UI state for Bi-Weekly Evaluation.
 *
 * @property biWeeklyEntry The current [BiWeeklyEvaluationEntry] for the user.
 * @property questionResponse The list of responses to the evaluation questions.
 * @property page The current page of the bi-weekly evaluation.
 */
data class BiWeeklyEvaluationUiState(
    val biWeeklyEntry: BiWeeklyEvaluationEntry = BiWeeklyEvaluationEntry(),
    val questionResponse: MutableList<Int> = (0..16).map { x -> x * 0 }.toMutableList(),
    val page: Int = 0,
)

/**
 * ViewModel for handling bi-weekly mood evaluation operations.
 *
 * This ViewModel manages the state of the bi-weekly evaluation, including navigating through the pages,
 * updating responses, and submitting the bi-weekly mood entry.
 *
 * @property biWeeklyMoodRepository Repository for handling bi-weekly mood data storage and retrieval.
 */
@HiltViewModel
class BiWeeklyEvaluationViewModel @Inject constructor(
    private val biWeeklyMoodRepository: BiWeeklyMoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BiWeeklyEvaluationUiState())
    val uiState: StateFlow<BiWeeklyEvaluationUiState> = _uiState.asStateFlow()

    /**
     * Increments the page count and triggers the submission if it's the last page.
     */
    fun onNext() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page + 1)
        }

        if (_uiState.value.page == _uiState.value.questionResponse.size - 1)
            submitMoodEntry()
    }

    /**
     * Decrements the page count.
     */
    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

    /**
     * Updates the response for the current question.
     *
     * @param selected The new response for the current question.
     */
    fun onSelect(selected: Int) {
        val newList = _uiState.value.questionResponse.toMutableList()
        newList[_uiState.value.page] = selected

        _uiState.update { currentState ->
            currentState.copy(
                questionResponse = newList
            )
        }
    }

    /**
     * Debugging function that calculates and logs the depression and anxiety scores based on the responses.
     */
    fun updateScore() {
        val depressionScore = _uiState.value.questionResponse.subList(0, 9).sum()
        val anxietyScore = _uiState.value.questionResponse.subList(9, 15).sum()

        _uiState.update { currentState ->
            currentState.copy(
                biWeeklyEntry = currentState.biWeeklyEntry.copy(
                    depressionScore = depressionScore,
                    anxietyScore = anxietyScore,
                    dateCompleted = Date.from(Instant.now())
                )
            )
        }

        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: $depressionScore")
        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Anxiety Score: $anxietyScore")
    }

    /**
     * Submits the bi-weekly mood entry to the repository.
     */
    private fun submitMoodEntry() {
        updateScore()

        viewModelScope.launch {
            biWeeklyMoodRepository.addMoodEntry(
                _uiState.value.biWeeklyEntry.copy(
                    dateCompleted = Date.from(Instant.now())
                )
            )
        }
    }
}

/**
 * UI state for the Evaluation Menu/Landing Page.
 *
 * @property isBiWeeklyCompleted Flag indicating if the bi-weekly evaluation has been completed.
 * @property isDailyEntry Flag indicating if the daily evaluation entry has been completed.
 */
data class EvaluationMenuUiState(
    val isBiWeeklyCompleted: Boolean = false,
    val isDailyEntry: Boolean = false
)

/**
 * ViewModel for managing the evaluation menu, tracking completion status of daily and bi-weekly evaluations.
 *
 * This ViewModel checks whether the daily or bi-weekly evaluation has been completed for the current date.
 *
 * @property dailyMoodRepository Repository for daily mood data.
 * @property biWeeklyMoodRepository Repository for bi-weekly mood data.
 */
@HiltViewModel
class EvaluationMenuViewModel @Inject constructor(
    private val dailyMoodRepository: DailyMoodRepository,
    private val biWeeklyMoodRepository: BiWeeklyMoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EvaluationMenuUiState())
    val uiState: StateFlow<EvaluationMenuUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { c ->
                c.copy(
                    isBiWeeklyCompleted = biWeeklyMoodRepository.fetchLatestMoodEntries()
                        .any { it.dateCompleted?.toLocalDate()?.equals(LocalDate.now()) == true },

                    isDailyEntry = dailyMoodRepository.fetchMoodEntries()
                        .any { it.dateCompleted?.toLocalDate()?.equals(LocalDate.now()) == true }
                )
            }
        }
    }
}
