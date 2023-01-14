package com.example.thindie.themathgame.data.gameLogic.engine

import com.example.thindie.themathgame.data.gameLogic.GameLogicActor
import com.example.thindie.themathgame.domain.entities.GameResults
import com.example.thindie.themathgame.domain.entities.GameSettings
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase


class ResultInGameBuilder(
    private val gameLogicActor: GameLogicActor,
    private val gameSettings: GameSettings
) {

    private val answerCollector = mutableListOf<AnswerScoreCounter>()

    private fun gameOver() {
        val gameResults: GameResults
        TODO()
        // gameLogicActor.onResult(gameResults)
    }

    fun collectScore(responce: OnUserResponceUseCase.Responce) {
        if (responce is OnUserResponceUseCase.Responce.Right) {
            val score: Int = if (responce.timeSpend != IS_GAME_OVER.toLong()) {
                scoreCalculator(responce.timeSpend, gameSettings)

            } else {
                IS_GAME_OVER
            }
            calculateResult(score)
            answerCollector.add(AnswerScoreCounter.RightAnswer(score = score))
        } else {
            answerCollector.add(AnswerScoreCounter.WrongAnswer())
        }
    }

    private fun calculateResult(score: Int) {
        if (score == IS_GAME_OVER) gameOver()
        else {
            val gameResults: GameResults = GameResults(
                null,
                null,
                null,
                gameScore = score,
                null
            )
            gameLogicActor.onResult(gameResults)
        }
    }


    private fun scoreCalculator(time: Long, gameSettings: GameSettings): Int {
        val amplifier: Double =
            when (gameSettings.level) {
                Level.NORMAl -> HARD_LEVEL_AMPLIFIER
                Level.HARD -> TOP_LEVEL_AMPLIFIER
                else -> NEVERMIND
            }
        val timeSpend = System.currentTimeMillis() - time

        return if (timeSpend / SCORE_DELIMETER < TOP_SCORE_LEVEL) {
            (TOP_SCORE * amplifier).toInt()
        } else if (timeSpend / SCORE_DELIMETER < MEDIUM_SCORE_LEVEL) {
            (MEDIUM_SCORE * amplifier).toInt()
        } else {
            LOW_SCORE
        }

    }

    sealed class AnswerScoreCounter {
        data class WrongAnswer(val score: Int = WRONG_ANSWER) : AnswerScoreCounter()
        data class RightAnswer(val score: Int) : AnswerScoreCounter()
    }

    companion object {
        private const val SCORE_DELIMETER = 100
        private const val TOP_SCORE_LEVEL = 100
        private const val MEDIUM_SCORE_LEVEL = 200
        private const val TOP_SCORE = 100
        private const val MEDIUM_SCORE = 75
        private const val LOW_SCORE = 50
        private const val TOP_LEVEL_AMPLIFIER = 2.5
        private const val HARD_LEVEL_AMPLIFIER = 1.7
        private const val IS_GAME_OVER = -1
        private const val WRONG_ANSWER = 0
        private const val NEVERMIND = 1.0

        fun build(gameSettings: GameSettings, gameLogicActor: GameLogicActor): ResultInGameBuilder {
            return ResultInGameBuilder(gameLogicActor, gameSettings)
        }
    }
}

