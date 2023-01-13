package com.example.thindie.themathgame.domain.useCase

import com.example.thindie.themathgame.domain.entities.Level
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OnUserResponceUseCase @Inject constructor(private val mathGameRepository: MathGameRepository) {
    suspend operator fun invoke(level: Level?, right: Unit?) {
        if (level != null) return mathGameRepository.onResponce(flow = flow {
            emit(
                Responce.Setting(
                    level
                )
            )
        }.flowOn(Dispatchers.IO))

        return if (right != null) mathGameRepository.onResponce(
            flow { emit(Responce.Right(unit = right)) }.flowOn(
                Dispatchers.IO
            )
        )

        else mathGameRepository.onResponce(
            flow { emit(Responce.Wrong(unit = Unit)) }.flowOn(
                Dispatchers.IO
            )
        )
    }

    sealed class Responce {
        data class Setting(val level: Level) : Responce()
        data class Wrong(val unit: Unit) : Responce()
        data class Right(val unit: Unit) : Responce()
    }


}

