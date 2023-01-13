package com.example.thindie.themathgame.domain.useCase

import com.example.thindie.themathgame.domain.entities.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OnUserRequestUseCase @Inject constructor(private val mathGameRepository: MathGameRepository) {
    suspend operator fun invoke(): Flow<Question> {
        return mathGameRepository.onRequest().flowOn(Dispatchers.Default)
    }
}