package com.example.thindie.themathgame.data.gameLogic.engine

import com.example.thindie.themathgame.domain.entities.GameSettings
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase


class QuestionGenerator(private val gotLevel: OnUserResponceUseCase.Responce.Setting) {

    private val gameSettings: GameSettings = initGameSettings()

    private fun initGameSettings(): GameSettings {
        when (gotLevel.level) {
            Level.TEST -> {
                return GameSettings(
                    7,
                    3,

                    Level.TEST
                )
            }
            Level.EASY -> {
                return GameSettings(
                    25,
                    10,

                    Level.EASY
                )
            }
            Level.NORMAl -> {
                return GameSettings(
                    35,
                    20,

                    Level.NORMAl
                )
            }
            Level.HARD -> {
                return GameSettings(
                    35,
                    25,

                    Level.HARD
                )
            }
        }
    }

    fun generateQuestion(): Question {


        val shuffleList = mutableListOf<Int>()
        val list = hashSetOf<Int>()
        var visibleNumber = 0
        var sum = 0
        when (gameSettings.level) {
            Level.EASY -> {
                for (i in 0..EASY) {
                    shuffleList.add(i);
                }
                shuffleList.shuffle()
                sum = shuffleList[0]
                shuffleList.shuffle()
                visibleNumber = shuffleList[1]


            }
            Level.NORMAl -> {
                for (i in EASY..NORMAL) {
                    shuffleList.add(i);
                }
                shuffleList.shuffle()
                sum = shuffleList[0]
                shuffleList.shuffle()
                visibleNumber = shuffleList[1]

            }
            Level.HARD -> {
                for (i in NORMAL..HARD) {
                    shuffleList.add(i);
                }
                shuffleList.shuffle()
                sum = shuffleList[0]
                shuffleList.shuffle()
                visibleNumber = shuffleList[1]

            }
            else -> {}
        }
        if (sum < visibleNumber) {
            val buffer = sum
            sum = visibleNumber
            visibleNumber = buffer
        }

        val solution = sum - visibleNumber

        list.add(solution)

        for (it in solution until solution + SOLUTIONS_RANGE) {
            list.add(it)
        }
        for (it in solution downTo solution - SOLUTIONS_RANGE) {
            list.add(it)
        }


        return Question(
            sum = sum,
            visibleNumber = visibleNumber,
            solution = solution,
            listOfVariants = list.toMutableList().apply { shuffle() }
        )
    }

    fun getGameSettings(): GameSettings = gameSettings


    companion object {
        fun build(
            level: OnUserResponceUseCase.Responce.Setting
        ): QuestionGenerator {
            return QuestionGenerator(level)
        }

        private const val SOLUTIONS_RANGE = 3

        private const val EASY = 6
        private const val NORMAL = 20
        private const val HARD = 60
    }
}

