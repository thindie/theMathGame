package com.example.thindie.themathgame.data.localData

import com.example.thindie.themathgame.domain.entities.GameResults
import kotlinx.coroutines.flow.Flow

interface DataBaseActor {
    suspend fun checkResult(gameResults: GameResults) : Boolean
    suspend fun addGameResult(gameResults: Flow<GameResults>)
    suspend fun showAllWinners(): Flow<List<GameResults>>
}