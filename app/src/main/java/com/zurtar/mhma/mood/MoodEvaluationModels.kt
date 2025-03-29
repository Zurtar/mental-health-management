package com.zurtar.mhma.mood

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.data.MoodEntry
import com.zurtar.mhma.data.MoodRemoteDataSource
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

data class BiWeeklyEvaluationEntry(
    var depressionScore: Int,
    var anxietyScore: Int,
    var dateCompleted: LocalDate,
    var depressionResults: String = "",
    var anxietyResults: String = ""

)

data class DailyEvaluationEntry(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val isSubmitted: Int = 0,
    val strongestEmotion: String = "default_initial",
    val page: Int = 0,

    )

data class BiWeeklyEvaluationUiState(
    val depressionScore: Int = 0,
    val anxietyScore: Int = 0,
    val page: Int = 0,
    val questionResponse: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
)

data class DailyEvaluationUiState(
    val currentEmotion: String = "default_initial",
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(),
    val isSubmitted: Int = 0,
)

class DailyEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DailyEvaluationUiState())
    val uiState: StateFlow<DailyEvaluationUiState> = _uiState.asStateFlow()

    fun onSubmit() {
        _uiState.update { currentState ->
            currentState.copy(isSubmitted = 1)
        }
    }

    fun onNext() {

        _uiState.update { currentState ->
            val d_entry = DailyEvaluationEntry(
                selectedEmotions = currentState.dailyEntry.selectedEmotions,
                emotionIntensities = currentState.dailyEntry.emotionIntensities,
                page = currentState.dailyEntry.page + 1
            )
            currentState.copy(dailyEntry = d_entry)
        }
    }

    fun onBack() {
        _uiState.update { currentState ->
            val d_entry = DailyEvaluationEntry(
                selectedEmotions = currentState.dailyEntry.selectedEmotions,
                emotionIntensities = currentState.dailyEntry.emotionIntensities,
                page = currentState.dailyEntry.page - 1
            )
            currentState.copy(dailyEntry = d_entry)
        }
    }

    fun updateEmotion(emoji: ImageVector) {
        if (emoji == EmojiFrown) {
            _uiState.update { currentState ->
                currentState.copy(currentEmotion = "Upset")
            }
        } else if (emoji == EmojiNeutral) {
            _uiState.update { currentState ->
                currentState.copy(currentEmotion = "Neutral")
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(currentEmotion = "Happy")
            }
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
                page = currentState.dailyEntry.page
            )

            currentState.copy(dailyEntry = d_entry)
        }
    }

    fun updateIntensity(value: Float, index: Int) {

        val intensityList = _uiState.value.dailyEntry.emotionIntensities.toMutableList()
        intensityList[index] = value
        Log.println(Log.DEBUG, "DailyEval:: ", "$value")

        _uiState.update { currentState ->
            currentState.copy(
                dailyEntry = DailyEvaluationEntry(
                    selectedEmotions = currentState.dailyEntry.selectedEmotions,
                    emotionIntensities = intensityList,
                    page = currentState.dailyEntry.page
                )
            )
        }
        Log.println(Log.DEBUG, "DailyEval:: ", "$intensityList")
    }

    fun makeMap(
        selectedEmotions: List<String>,
        intensities: List<Float>
    ): MutableMap<String, Float> {
        val emotionsMap = mutableMapOf<String, Float>()

        for (i in 0..selectedEmotions.size) {
            emotionsMap[selectedEmotions[i]] = intensities[i]
        }
        return emotionsMap
    }


}

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
                depressionScore = depressionScore,
                anxietyScore = anxietyScore
            )
        }

        // Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: $depressionScore")
        //Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Anxiety Score: $anxietyScore")
    }

    fun submitMoodEntry() {
        debugScore();

        val entry = MoodEntry(
            depressionScore = _uiState.value.depressionScore,
            anxietyScore = -1,
            dateCompleted = Date()
        )
        viewModelScope.launch {
            moodRepository.addMoodEntry(entry)
        }
    }


    fun resetPage() {
        _uiState.update { currentState ->
            currentState.copy(page = 0)
        }
    }
}