package com.example.thindie.themathgame.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.themathgame.domain.entities.GameResults
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnRequestResultUseCase
import com.example.thindie.themathgame.domain.useCase.OnUserRequestUseCase
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onUserRequestUseCase: OnUserRequestUseCase,
    private val onUserResponceUseCase: OnUserResponceUseCase,
    private val onRequestResults: OnRequestResultUseCase
) : ViewModel() {

    val uiState: MutableStateFlow<UIResponce> = MutableStateFlow(UIResponce.Loading(Unit))
    val timerState: MutableStateFlow<String> = MutableStateFlow(TIMER_TAG)
    val resultState: MutableStateFlow<UIResponce.Result> =
        MutableStateFlow(
            UIResponce.Result(
                GameResults(
                    null,
                    null,
                    null,
                    INITIAL,
                    null
                )
            )
        )

    init {
        uiState.value = UIResponce.Loading(Unit)
    }

    fun setGame(level: Level) {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(level, null)
            uiState.value = UIResponce.Circular(Unit)
            delay(SECOND)
            onSetTimer()
            onRequestQuestion()
        }
    }

    fun onWrongAnswer() {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(null, null)
            onRequestScore()
            delay(WAIT_A_LITTLE)
            onRequestQuestion()
        }
    }

    fun onRightAnswer(timeSpend: Long) {
        viewModelScope.launch {
            uiState.value = UIResponce.Right(Unit)
            onUserResponceUseCase.invoke(null, timeSpend = timeSpend)
            onRequestScore()
            delay(WAIT_A_LITTLE)
            if (timeSpend > INITIAL) {
                onRequestQuestion()
            }
        }
    }

    private fun onSetTimer() {
        var timer = GAME_TIME
        viewModelScope.launch {
            do {
                delay(SECOND)
                timerState.value = String.format("time left : %d", timer--)
            } while (timer > IS_GAME_OVER)

            onRightAnswer(IS_GAME_OVER)
        }
    }

    private fun onRequestScore() {
        viewModelScope.launch {
            onRequestResults.invoke().collect {
                if (it.isWinner != null) {
                   Log.d("Service_tag", "Winner im vm")
                } else {
                    resultState.value = UIResponce.Result(it)
                }
            }

        }
    }

    private fun onRequestQuestion() {
        viewModelScope.launch {
            onUserRequestUseCase.invoke().collect {
                uiState.value = UIResponce.AskQuestion(it)
            }
        }
    }


    sealed class UIResponce {
        data class AskQuestion(val question: Question) : UIResponce()
        data class Circular(val unit: Unit) : UIResponce()
        data class Wrong(val unit: Unit) : UIResponce()
        data class Right(val unit: Unit) : UIResponce()
        data class Result(val gameResults: GameResults) : UIResponce()
        data class Loading(val unit: Unit) : UIResponce()
    }

    companion object {
        private const val GAME_TIME = 3
        private const val IS_GAME_OVER = -1L
        private const val INITIAL = 0
        private const val SECOND = 1000L
        private const val WAIT_A_LITTLE = 40L
        private const val TIMER_TAG = "time left : "
    }

}