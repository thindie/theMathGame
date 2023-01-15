package com.example.thindie.themathgame.data

import android.util.Log
import com.example.thindie.themathgame.data.gameLogic.GameLogicActor
import com.example.thindie.themathgame.data.localData.DataBaseActor
import com.example.thindie.themathgame.domain.entities.GameResults
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.MathGameRepository
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MathGameRepositoryImpl @Inject constructor(
    private val gameLogicActor: GameLogicActor,
    private val dataBaseActor: DataBaseActor
) :
    MathGameRepository {

    override suspend fun onRequest(): Flow<Question> {
        return gameLogicActor.onQuestion().flowOn(Dispatchers.IO)
    }

    override suspend fun onResponce(flow: Flow<OnUserResponceUseCase.Responce>) {
        gameLogicActor.onAnswer(flow)
    }

    override suspend fun onResult(showAllScores: Unit?): Flow<GameResults> {
        if(showAllScores != null){

        }

        gameLogicActor.onResult().collect {
            if (it.isWinner != null) {
                dataBaseActor //check & add
            }
        }
        return gameLogicActor.onResult()
    }
}
