package com.example.thindie.themathgame.data.localData

import com.example.thindie.themathgame.data.localData.dataBase.GameResultDao
import com.example.thindie.themathgame.data.localData.dataBase.GameResultMapper
import com.example.thindie.themathgame.domain.entities.GameResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseActorImpl @Inject constructor(
    private val gameResultMapper: GameResultMapper,
    private val gameResultDao: GameResultDao
) :
    DataBaseActor {
    override suspend fun checkResult(gameResults: GameResults): Boolean {
        var add = false
        if (gameResults.gameScore > CLUB_ENTRANCE) {
            gameResultDao.getAll().collect {
                if (it.gameScore < gameResults.gameScore) {
                    add = true
                }
            }
        }
        return add
    }

    override suspend fun addGameResult(gameResults: Flow<GameResults>) {
        gameResults.collect {
            if (checkResult(it)) {
                if (it.name == null) {
                    //ask name
                } else {
                    gameResultDao.addResult(gameResultMapper.fromObjToDao(it))
                }
            }
        }
    }

        override suspend fun showAllWinners(): Flow<GameResults> {
            TODO()
        }

        companion object {
            private const val CLUB_ENTRANCE = 800
        }

    }