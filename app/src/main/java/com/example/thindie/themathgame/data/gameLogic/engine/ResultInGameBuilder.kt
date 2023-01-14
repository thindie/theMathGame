package com.example.thindie.themathgame.data.gameLogic.engine

import com.example.thindie.themathgame.domain.entities.GameResults
import com.example.thindie.themathgame.domain.entities.GameSettings
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ResultInGameBuilder(
    private val gameSettings: GameSettings
) {

    private val answerCollector = mutableListOf<AnswerScoreCounter>()
    private val flowList = mutableListOf<Flow<GameResults>>()

    private fun gameOver(gameResults: GameResults): Flow<GameResults> {

        TODO()
        // gameLogicActor.onResult(gameResults)
    }

    fun flowOfResults(): Flow<GameResults> {
        if (!flowList.isEmpty()) return flowList[INITIAL]

        return flow {
            emit(
                GameResults(
                    null,
                    null,
                    null,
                    INITIAL,
                    null
                )
            )
        }
    }

    fun collectScore(responce: OnUserResponceUseCase.Responce) {
        if (responce is OnUserResponceUseCase.Responce.Right) {
            val score: Int = if (responce.timeSpend != IS_GAME_OVER.toLong()) {
                scoreCalculator(responce.timeSpend, gameSettings)

            } else {
                IS_GAME_OVER.apply {
                    countGameOverResults()
                }
            }

            answerCollector.add(AnswerScoreCounter.RightAnswer(score = score))
        } else {
            answerCollector.add(AnswerScoreCounter.WrongAnswer(-100))
        }
        calculateResult()
    }

    private fun calculateResult() {

        val results = GameResults(
            null,
            null,
            null,
            gameScore = collectAllScoresInAnswerCollector(),
            null
        )

        if (!flowList.isEmpty()) {
            flowList[INITIAL] = (
                    flow { emit(results) })
        } else {
            flowList.add(flow { emit(results) })
        }

    }

    private fun collectAllScoresInAnswerCollector(): Int {
        var scores = INITIAL
        answerCollector.forEach {
            if (it is AnswerScoreCounter.WrongAnswer) {
                scores += it.score
            } else if (it is AnswerScoreCounter.RightAnswer) {
                scores += it.score
            }
        }
        return scores
    }

    private fun countGameOverResults() : GameResults{
        val solvedQuestions = answerCollector.filter {
            it is AnswerScoreCounter.RightAnswer
        }.size
        val totalQuestions = answerCollector.size
        val gameScore = collectAllScoresInAnswerCollector()
        val winRate = (( solvedQuestions * NEVERMIND / totalQuestions ) * BECOME_PERCENT).toInt()
        return GameResults(solvedQuestions,totalQuestions,true, gameScore, winRate)
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
        data class WrongAnswer(val score: Int) : AnswerScoreCounter()
        data class RightAnswer(val score: Int) : AnswerScoreCounter()
    }

    companion object {
        private const val BECOME_PERCENT = 100
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
        private const val INITIAL = 0
        private const val NEVERMIND = 1.0

        fun build(gameSettings: GameSettings): ResultInGameBuilder {
            return ResultInGameBuilder(gameSettings)
        }
    }
}

