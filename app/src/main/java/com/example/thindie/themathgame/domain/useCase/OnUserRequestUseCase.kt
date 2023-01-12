package com.example.thindie.themathgame.domain.useCase

import com.example.thindie.themathgame.domain.entities.Question
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnUserRequestUseCase @Inject constructor(private val mathGameRepository: MathGameRepository) {
    suspend operator fun invoke(): Flow<Question> {
        return mathGameRepository.onRequest()
    }
}