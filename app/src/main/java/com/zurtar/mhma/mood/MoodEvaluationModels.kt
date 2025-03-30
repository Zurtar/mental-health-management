package com.zurtar.mhma.mood

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.analytics.toDate
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.data.MoodRepository
import com.zurtar.mhma.theme.EmojiFrown
import com.zurtar.mhma.theme.EmojiNeutral
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.time.LocalDate
import javax.inject.Inject

data class DailyEvaluationEntry(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val stressLevel: String = "default_initial",
    val strongestEmotion: Pair<String, Float> = "" to 0f,
    val dateCompleted: Date? = null
)
/**
 * Daily/Quick Evaluation UI State & ViewModel
 */
data class DailyEvaluationUiState(
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(),
    val isSubmitted: Int = 0,
    val page: Int = 0
)

class DailyEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DailyEvaluationUiState())
    val uiState: StateFlow<DailyEvaluationUiState> = _uiState.asStateFlow()

    fun onSubmit() {

        val emotionsMap: Map<String, Float> =
            _uiState.value.dailyEntry.selectedEmotions.zip(_uiState.value.dailyEntry.emotionIntensities).sortedByDescending { (_, intensity) ->
                intensity
            }.toMap()

        _uiState.update { currentState ->
            currentState.copy(
                isSubmitted = 1,
                dailyEntry = currentState.dailyEntry.copy(
                    emotionsMap = emotionsMap,
                    dateCompleted = LocalDate.now().toDate()
                )
            )
        }
    }

    fun onNext() {

        _uiState.update { currentState ->
            currentState.copy(page = currentState.page + 1)
        }
    }

    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

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

    fun updateIntensity(value: Float, index: Int) {

        val intensityList = _uiState.value.dailyEntry.emotionIntensities.toMutableList()
        intensityList[index] = value
        Log.println(Log.DEBUG, "DailyEval:: ", "$value")

        val emotionsMap: Map<String, Float> =
            _uiState.value.dailyEntry.selectedEmotions.zip(intensityList).sortedByDescending { (_, intensity) ->
                intensity
            }.toMap()


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
 * Bi-Weekly Evaluation UI State & ViewModel
 */
data class BiWeeklyEvaluationUiState(
    val biWeeklyEntry: BiWeeklyEvaluationEntry = BiWeeklyEvaluationEntry(),
    val questionResponse: MutableList<Int> = (0..16).map { x -> x * 0 }.toMutableList(),
    val page: Int = 0,
)

@HiltViewModel
class BiWeeklyEvaluationViewModel @Inject constructor(
    private val moodRepository: MoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BiWeeklyEvaluationUiState())
    val uiState: StateFlow<BiWeeklyEvaluationUiState> = _uiState.asStateFlow()


    /**
     * Triggered when Next button is pressed
     * Should increment page count and possibly do verification
     */
    fun onNext() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page + 1)
        }

        if (_uiState.value.page == _uiState.value.questionResponse.size)
            submitMoodEntry()
    }

    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

    fun onSelect(selected: Int) {
        val newList = _uiState.value.questionResponse.toMutableList()
        newList[_uiState.value.page] = selected

        _uiState.update { currentState ->
            currentState.copy(
                questionResponse = newList
            )
        }
    }


    fun debugScore() {
        val depressionScore = _uiState.value.questionResponse.subList(0, 9).sum()
        val anxietyScore = _uiState.value.questionResponse.subList(9, 15).sum()

        _uiState.update { currentState ->
            currentState.copy(
                biWeeklyEntry = currentState.biWeeklyEntry.copy(
                    depressionScore = depressionScore,
                    anxietyScore = anxietyScore
                )
            )
        }

        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: $depressionScore")
        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Anxiety Score: $anxietyScore")
    }

    private fun submitMoodEntry() {
        debugScore();

        viewModelScope.launch {
            moodRepository.addMoodEntry(
                _uiState.value.biWeeklyEntry
            )
        }
    }

}
/**
 * Evaluation Menu/Landing Page UI State & ViewModel
 */
data class EvaluationMenuUiState(
    val isBiWeeklyCompleted: Boolean = false,
    val isDailyEntry: Boolean = false
)

class EvaluationMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationMenuUiState())
    val uiState: StateFlow<EvaluationMenuUiState> = _uiState.asStateFlow()


    fun updateBiWeekly() {
        if (!_uiState.value.isBiWeeklyCompleted) {
            _uiState.update { currentState ->
                currentState.copy(isBiWeeklyCompleted = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(isBiWeeklyCompleted = false)
            }
        }

    }

}