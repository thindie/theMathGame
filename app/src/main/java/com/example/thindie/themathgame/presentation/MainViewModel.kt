package com.example.thindie.themathgame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserRequestUseCase
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onUserRequestUseCase: OnUserRequestUseCase,
    private val onUserResponceUseCase: OnUserResponceUseCase
) : ViewModel() {

    val _uiState : MutableStateFlow<UIResponce> = MutableStateFlow(UIResponce.Loading(Unit))

    init {
        _uiState.value = UIResponce.Loading(Unit)
    }

    fun setGame(level: Level) {
        viewModelScope.launch {
            val setGameFlow: Flow<OnUserResponceUseCase.Responce> = flow {
                onUserResponceUseCase.invoke(level, null)
            }
        }
    }

    fun wrongAnswer() {
        viewModelScope.launch {
            val setGameFlow: Flow<OnUserResponceUseCase.Responce> = flow {
                onUserResponceUseCase.invoke(null, null)
            }
        }
    }

    fun rightAnswer() {
        viewModelScope.launch {
            val setGameFlow: Flow<OnUserResponceUseCase.Responce> = flow {
                onUserResponceUseCase.invoke(null, Unit)
            }
        }
    }

    fun requestQuestion() {
        viewModelScope.launch {
            onUserRequestUseCase.invoke().collect{
                _uiState.value = UIResponce.AskQuestion(it)
            }
        }
    }


    sealed class UIResponce {
        data class AskQuestion(val question: Question) : UIResponce()
        data class Wrong(val unit: Unit) : UIResponce()
        data class Right(val unit: Unit) : UIResponce()
        data class Result(val unit: Unit) : UIResponce()
        data class Loading(val unit: Unit) : UIResponce()
    }


}