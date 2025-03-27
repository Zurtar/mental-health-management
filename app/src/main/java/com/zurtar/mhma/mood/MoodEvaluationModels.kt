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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

data class BiWeeklyEvaluationUiState(
    val score: Int = 0,
    val page: Int = 0,
    val questionResponse: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
)

data class DailyEvaluationUiState(
    val currentEmotion: String = "default_initial",
    // val emotionResponse:List<String> = listOf(0, 0, 0, 0),
    val isSubmitted: Int = 0,
    val strongestEmotion: String = "default_initial"

)

class DailyEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DailyEvaluationUiState())
    val uiState: StateFlow<DailyEvaluationUiState> = _uiState.asStateFlow()

    fun onSubmit() {
        _uiState.update { currentState ->
            currentState.copy(isSubmitted = 1)
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
//
//        val newList = _uiState.value.emotionResponse.toMutableList()
//        var index = 0
//        if(emotion == "Happy"){
//            index = 1
//        }else if(emotion == "Fearful") {
//            index = 2
//        } else if (emotion == "Angry") {
//            index = 3
//        } else {
//            index = 0
//        }
//        newList[index] = _uiState.value.emotionResponse[index] +1
//
//        _uiState.update { currentState ->
//            currentState.copy(
//                emotionResponse = newList
//            )
//        }
        _uiState.update { currentState ->
            currentState.copy(strongestEmotion = emotion)
        }
    }

}

class BiWeeklyEvaluationViewModel : ViewModel() {

    private val moodRepository = MoodRepository(moodRemoteDataSource = MoodRemoteDataSource())

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
        val score = _uiState.value.questionResponse.sum()
        _uiState.update { currentState ->
            currentState.copy(score = score)
        }

        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "$score")
    }

    fun submitMoodEntry() {
        debugScore();

        val entry = MoodEntry(
            depressionScore = _uiState.value.score,
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