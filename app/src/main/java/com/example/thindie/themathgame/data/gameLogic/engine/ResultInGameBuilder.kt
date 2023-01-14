package com.example.thindie.themathgame.data.gameLogic.engine

import com.example.thindie.themathgame.data.gameLogic.GameLogicActor
import com.example.thindie.themathgame.domain.entities.GameSettings
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase


class ResultInGameBuilder(
    private val gameLogicActor: GameLogicActor,
    private val gameSettings: GameSettings
) {

    private val taskCollector = mutableListOf<AnswerCollector>()


    fun collectScore(responce: OnUserResponceUseCase.Responce) {
        if (responce is OnUserResponceUseCase.Responce.Right) {
            val score = if (responce.timeSpend >= 0) {
                scoreCalculator(responce.timeSpend, gameSettings)
            } else {
                0
            }

            calculateResult(score)
        }
    }

    private fun calculateResult(score: Int) {

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

    sealed class AnswerCollector {
        data class WrongAnswer(val score: Int = 0) : AnswerCollector()
        data class RightAnswer(val score: Int) : AnswerCollector()
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
        private const val NEVERMIND = 1.0

        fun build(gameSettings: GameSettings, gameLogicActor: GameLogicActor): ResultInGameBuilder {
            return ResultInGameBuilder(gameLogicActor, gameSettings)
        }
    }
}

