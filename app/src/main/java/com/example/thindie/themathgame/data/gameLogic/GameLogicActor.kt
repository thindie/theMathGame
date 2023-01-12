package com.example.thindie.themathgame.data.gameLogic

import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import kotlinx.coroutines.flow.Flow

interface GameLogicActor {
    suspend fun onAnswer(task : Flow<OnUserResponceUseCase.Responce>)
    fun onQuestion(): Flow<Question>
}