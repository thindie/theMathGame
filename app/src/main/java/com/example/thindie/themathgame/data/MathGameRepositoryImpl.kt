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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
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

    //check is winner and return result to UI - to show or to Save
    override suspend fun onResult(showAllScores: Unit?): Flow<GameResults> {
        if (showAllScores != null) {
            return flow {
                dataBaseActor.showAllWinners().collect {
                    it.forEach {
                        this.emit(it)
                    }
                }
            }
        }


        var requestOnUserData = false
        var gameResultsToSetName: GameResults? = null

        gameLogicActor.onResult().collect {
            if (it.isWinner == true) {
                if (dataBaseActor.checkResult(it)) { // if Result is record
                    requestOnUserData = true  //  obj to ask
                    gameResultsToSetName = it  //
                }
            }
        }


        if (requestOnUserData) return flow {
            val copyResult = gameResultsToSetName?.copy(
                name = REQUEST_NAME
            )
            if (copyResult != null) {
                emit(
                    copyResult
                )
                Log.d("SERVICE_TAG", "TO ADD")
            }
        } // return obj to ask
        return gameLogicActor.onResult()
    }

    override suspend fun <T> addData(flow: Flow<T>) {

        dataBaseActor.addGameResult(flow as Flow<GameResults>)
    }

    companion object {
        private const val REQUEST_NAME = "requestName"
    }
}
