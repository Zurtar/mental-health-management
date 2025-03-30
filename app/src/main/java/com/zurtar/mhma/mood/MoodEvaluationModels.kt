package com.zurtar.mhma.mood

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.zurtar.mhma.theme.EmojiFrown
import com.zurtar.mhma.theme.EmojiNeutral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class BiWeeklyEvaluationEntry(
    var depressionScore: Int = 0,
    var anxietyScore: Int = 0,
    var dateCompleted: LocalDate,
    var depressionResults: String = "",
    var anxietyResults: String = "",
    val questionResponse: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

)

data class DailyEvaluationEntry(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val currentEmotion: String = "default_initial",
    val strongestEmotion: String = "",
    val dateCompleted: LocalDate
)

data class BiWeeklyEvaluationUiState(
    val biWeeklyEntry: BiWeeklyEvaluationEntry = BiWeeklyEvaluationEntry(dateCompleted = LocalDate.now()),
    val page: Int = 0,

)

data class EvaluationMenuUiState(
    val isBiWeeklyCompleted: Boolean = false,
    val isDailyEntry: Boolean  = false
)

data class DailyEvaluationUiState(
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(dateCompleted = LocalDate.now()),
    val isSubmitted: Int = 0,
    val page: Int = 0
)


class DailyEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DailyEvaluationUiState())
    val uiState: StateFlow<DailyEvaluationUiState> = _uiState.asStateFlow()

    fun onSubmit() {
        _uiState.update { currentState ->
            currentState.copy(isSubmitted = 1,
                dailyEntry = currentState.dailyEntry.copy(dateCompleted = LocalDate.now()
            ))
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
        if (emoji == EmojiFrown) {
            _uiState.update { currentState ->
                currentState.copy(
                    dailyEntry = DailyEvaluationEntry(
                        selectedEmotions = currentState.dailyEntry.selectedEmotions,
                        emotionIntensities = currentState.dailyEntry.emotionIntensities,
                        currentEmotion = "Very Stressed",
                        dateCompleted = currentState.dailyEntry.dateCompleted
                    )
                )
            }
        } else if (emoji == EmojiNeutral) {
            _uiState.update { currentState ->
                currentState.copy(
                    dailyEntry = DailyEvaluationEntry(
                        selectedEmotions = currentState.dailyEntry.selectedEmotions,
                        emotionIntensities = currentState.dailyEntry.emotionIntensities,
                        currentEmotion = "Mildly Stressed",
                        dateCompleted = currentState.dailyEntry.dateCompleted
                    )
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    dailyEntry = DailyEvaluationEntry(
                        selectedEmotions = currentState.dailyEntry.selectedEmotions,
                        emotionIntensities = currentState.dailyEntry.emotionIntensities,
                        currentEmotion = "Not Stressed",
                        dateCompleted = currentState.dailyEntry.dateCompleted
                    )
                )
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
                currentEmotion = currentState.dailyEntry.currentEmotion,
                dateCompleted = currentState.dailyEntry.dateCompleted
            )

            currentState.copy(dailyEntry = d_entry)
        }
    }

    fun updateIntensity(value: Float, index: Int) {

        val intensityList = _uiState.value.dailyEntry.emotionIntensities.toMutableList()
        intensityList[index] = value
        Log.println(Log.DEBUG, "DailyEval:: ", "$value")

        var emotionsMap: Map<String, Float> =
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

}

class BiWeeklyEvaluationViewModel : ViewModel() {
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

        if (_uiState.value.page == _uiState.value.biWeeklyEntry.questionResponse.size)
            debugScore()
    }

    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

    fun onSelect(selected: Int) {
        val newList = _uiState.value.biWeeklyEntry.questionResponse.toMutableList()
        newList[_uiState.value.page] = selected

        _uiState.update { currentState ->
            currentState.copy(
                biWeeklyEntry = currentState.biWeeklyEntry.copy(
                    questionResponse = newList
                )
            )
        }
    }

    fun debugScore() {
        val depressionScore = _uiState.value.biWeeklyEntry.questionResponse.subList(0, 9).sum()
        val anxietyScore = _uiState.value.biWeeklyEntry.questionResponse.subList(9, 15).sum()

        _uiState.update { currentState ->
            currentState.copy(
                biWeeklyEntry = currentState.biWeeklyEntry.copy(
                    depressionScore = depressionScore,
                    anxietyScore = anxietyScore
                )
            )
        }

        // Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: $depressionScore")
        //Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Anxiety Score: $anxietyScore")
    }

    fun resetPage() {
        _uiState.update { currentState ->
            currentState.copy(page = 0)
        }
    }
}

class EvaluationMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationMenuUiState())
    val uiState: StateFlow<EvaluationMenuUiState> = _uiState.asStateFlow()

//
//    fun updateBiWeekly() {
//        if (!_uiState.value.biWeeklyCompleted) {
//            _uiState.update { currentState ->
//                currentState.copy(biWeeklyCompleted = true)
//            }
//        } else {
//            _uiState.update { currentState ->
//                currentState.copy(biWeeklyCompleted = false)
//            }
//        }
//
//    }

}