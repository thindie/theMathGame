package com.example.thindie.themathgame.domain.useCase

import com.example.thindie.themathgame.domain.entities.GameResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class OnRequestResultUseCase @Inject constructor(private val mathGameRepository: MathGameRepository) {
    suspend operator fun invoke(showAllScores: Unit?): Flow<GameResults> {
        if (showAllScores == null) {
            return mathGameRepository.onResult(null)
                .flowOn(Dispatchers.Default)
        }
        return mathGameRepository.onResult(showAllScores)
            .flowOn(Dispatchers.Default)
    }
}
