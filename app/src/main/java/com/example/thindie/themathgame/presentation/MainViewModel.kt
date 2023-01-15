package com.example.thindie.themathgame.presentation

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

    val uiState: MutableStateFlow<UIResponce> = MutableStateFlow(UIResponce.Loading(LOADING))
    val timerState: MutableStateFlow<String> = MutableStateFlow(TIMER_TAG)
    val resultState: MutableStateFlow<UIResponce.Result> =
        MutableStateFlow(
            UIResponce.Result(
                INITIAL_RESULT
            )
        )

    init {
        loadNewGame()
    }

    fun onShowWinners() {

        viewModelScope.launch {
            val list: List<GameResults> = mutableListOf()
            onRequestResults.invoke(COLLECT_ALL_WINNERS).collect {

            }
        }
    }

    fun loadNewGame() {
        uiState.value = UIResponce.Loading(LOADING)
        resultState.value = UIResponce.Result(INITIAL_RESULT)
    }

    fun setGame(level: Level) {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(level, THIS_PARAM_IS_OKAY)
            uiState.value = UIResponce.Circular(LOADING)
            delay(SECOND)
            onSetTimer()
            onRequestQuestion()
        }
    }

    fun onWrongAnswer() {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(THIS_PARAM_IS_OKAY, THIS_PARAM_IS_OKAY)
            onRequestScore()
            delay(WAIT_A_LITTLE)
            onRequestQuestion()
        }
    }

    fun onRightAnswer(timeSpend: Long) {
        viewModelScope.launch {
            uiState.value = UIResponce.Right(LOADING)
            onUserResponceUseCase.invoke(THIS_PARAM_IS_OKAY, timeSpend = timeSpend)
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
                timerState.value = String.format(TIMER_TAG.plus("%d"), timer--)
            } while (timer > IS_GAME_OVER)

            onRightAnswer(IS_GAME_OVER)
        }
    }

    private fun onRequestScore() {
        viewModelScope.launch {
            onRequestResults.invoke(COLLECT_IN_GAME_RESULT).collect {
                if (it.isWinner != null) {
                    uiState.value = UIResponce.Circular(LOADING)
                    delay(SECOND)
                    uiState.value = UIResponce.ShowScores(listOf(it))
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
        data class ShowScores(val list: List<GameResults>) : UIResponce()
        data class Loading(val unit: Unit) : UIResponce()
    }

    companion object {
        private val COLLECT_IN_GAME_RESULT = null
        private val COLLECT_ALL_WINNERS = Unit
        private const val GAME_TIME = 25
        private const val IS_GAME_OVER = -1L
        private const val INITIAL = 0
        private const val SECOND = 1000L
        private const val WAIT_A_LITTLE = 100L
        private const val TIMER_TAG = "time left : "
        private val LOADING = Unit
        private val THIS_PARAM_IS_OKAY = null
        private val INITIAL_RESULT = GameResults(
            null,
            null,
            null,
            INITIAL,
            null,
            null
        )
    }

}