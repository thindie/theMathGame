package com.example.thindie.themathgame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.entities.Question
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
    private val onUserResponceUseCase: OnUserResponceUseCase
) : ViewModel() {

    val _uiState: MutableStateFlow<UIResponce> = MutableStateFlow(UIResponce.Loading(Unit))

    init {
        _uiState.value = UIResponce.Loading(Unit)
    }

    fun setGame(level: Level) {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(level, null)
            _uiState.value = UIResponce.Circular(Unit)
            delay(1000)
            requestQuestion()
        }
    }

    fun wrongAnswer() {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(null, null)
        }
    }

    fun rightAnswer() {
        viewModelScope.launch {
            onUserResponceUseCase.invoke(null, Unit)
        }
    }

    fun requestQuestion() {
        viewModelScope.launch {
            onUserRequestUseCase.invoke().collect {
                val question = it
                _uiState.value = UIResponce.AskQuestion(it)
            }
        }
    }


    sealed class UIResponce {
        data class AskQuestion(val question: Question) : UIResponce()
        data class Circular(val unit: Unit) : UIResponce()
        data class Wrong(val unit: Unit) : UIResponce()
        data class Right(val unit: Unit) : UIResponce()
        data class Result(val unit: Unit) : UIResponce()
        data class Loading(val unit: Unit) : UIResponce()
    }


}