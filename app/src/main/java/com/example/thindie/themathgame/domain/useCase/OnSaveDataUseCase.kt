package com.example.thindie.themathgame.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OnSaveDataUseCase<T> @Inject constructor(private val mathGameRepository: MathGameRepository) {
    suspend operator fun <T> invoke(t: Flow<T>) {
        mathGameRepository.addData(t.flowOn(Dispatchers.IO))
    }
}