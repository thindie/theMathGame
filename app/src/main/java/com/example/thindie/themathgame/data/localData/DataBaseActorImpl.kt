package com.example.thindie.themathgame.data.localData

import com.example.thindie.themathgame.data.localData.dataBase.GameResultDao
import com.example.thindie.themathgame.data.localData.dataBase.GameResultDbModel
import com.example.thindie.themathgame.data.localData.dataBase.GameResultMapper
import com.example.thindie.themathgame.domain.entities.GameResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseActorImpl @Inject constructor(
    private val gameResultMapper: GameResultMapper,
    private val gameResultDao: GameResultDao
) :
    DataBaseActor {
    override suspend fun checkResult(gameResults: GameResults): Boolean {
        var add = false
        if (gameResults.gameScore > CLUB_ENTRANCE) {
            //some logic
            add = true
        }

        return add
    }


    override suspend fun addGameResult(gameResults: Flow<GameResults>) {
        var gameResultsDao: GameResultDbModel? = null
        gameResults.collect {
            if (it.name != null) {
                gameResultsDao = gameResultMapper.fromObjToDao(
                    it.copy(
                        solvedQuestions = it.solvedQuestions,
                        totalQuestions = it.totalQuestions,
                        isWinner = it.isWinner,
                        gameScore = it.gameScore,
                        winRate = it.winRate,
                        name = it.name
                    )
                )

            } else {
                throw RuntimeException("SomethingWrong!")
            }
        }
        withContext(context = Dispatchers.IO) {
            gameResultsDao?.let { gameResultDao.addResult(it) }
        }
    }


    override suspend fun showAllWinners(): Flow<List<GameResults>> {

        return flow {
            gameResultDao.getAll().collect {
                emit(gameResultMapper.fromDAOToObj(it))

            }
        }
    }


    companion object {
        private const val CLUB_ENTRANCE = 800
    }

}