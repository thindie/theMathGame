package com.example.thindie.themathgame.domain.useCase

import com.example.thindie.themathgame.domain.entities.Question
import kotlinx.coroutines.flow.Flow

interface MathGameRepository {
    suspend fun onRequest(): Flow<Question>
    suspend fun onResponce(flow : Flow<OnUserResponceUseCase.Responce>)
}