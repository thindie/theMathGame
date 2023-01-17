package com.example.thindie.themathgame.data.localData.dataBase

import com.example.thindie.themathgame.domain.entities.GameResults
import javax.inject.Inject

class GameResultMapper @Inject constructor() {
    fun fromObjToDao(gameResults: GameResults): GameResultDbModel {
        return GameResultDbModel(
            gameResults.name ?: throw Exception("Where is my name?"),
            gameResults.solvedQuestions ?: throw Exception("Where is my SQ?"),
            gameResults.totalQuestions ?: throw Exception("Where is my TQ?"),
            gameResults.gameScore,
            gameResults.winRate ?: throw Exception("Where is my WINRATE?"),
        )
    }

    fun fromDAOToObj(gameResultsDBModel: List<GameResultDbModel>): List<GameResults> {
        val resultList = mutableListOf<GameResults>()
        gameResultsDBModel.forEach { gameResults ->
            resultList.add(
                GameResults(
                    gameResults.solvedQuestions,
                    gameResults.totalQuestions ,
                    true,
                    gameResults.gameScore,
                    gameResults.winRate,
                    gameResults.name
                )
            )
        }
        return resultList
    }

}