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

    val _uiState: MutableStateFlow<UIResponce> = MutableStateFlow(UIResponce.Loading(Unit))
    val _resultState: MutableStateFlow<UIResponce.Result> =
        MutableStateFlow(
            UIResponce.Result(
                GameResults(
                    null,
                    null,
                    null,
                    0,
                    null
                )
            )
        )

    init {
        _uiState.value = UIResponce.Loading(Unit)
    }

    fun setGame(level: Level) {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(level, null)
            _uiState.value = UIResponce.Circular(Unit)
            delay(1000)
            onRequestQuestion()
        }
    }

    fun onWrongAnswer() {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(null, null)
            onRequestScore()
            delay(20)
            onRequestQuestion()
        }
    }

    fun onRightAnswer(timeSpend: Long) {
        viewModelScope.launch {
            _uiState.value = UIResponce.Right(Unit)
            onUserResponceUseCase.invoke(null, timeSpend = timeSpend)
            onRequestScore()
            delay(20)
            onRequestQuestion()
        }
    }

    private fun onRequestScore() {
        viewModelScope.launch {
            onRequestResults.invoke().collect {
                if (it.isWinner != null) {
                    TODO()
                }
                else{
                    _resultState.value = UIResponce.Result(it)
                }
            }

        }
    }

    private fun onRequestQuestion() {
        viewModelScope.launch {
            onUserRequestUseCase.invoke().collect {
                _uiState.value = UIResponce.AskQuestion(it)
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


}