package com.example.thindie.themathgame.data.gameLogic.engine

import com.example.thindie.themathgame.domain.entities.GameSettings
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import java.util.*


class QuestionGenerator(private val gotLevel: OnUserResponceUseCase.Responce.Setting) {

    private val gameSettings: GameSettings = initGameSettings()
    

    private fun initGameSettings(): GameSettings {
        when (gotLevel.level) {
            Level.TEST -> {
                return GameSettings(
                    7,
                    3,
                    50,
                    Level.TEST
                )
            }
            Level.EASY -> {
                return GameSettings(
                    25,
                    10,
                    50,
                    Level.EASY
                )
            }
            Level.NORMAl -> {
                return GameSettings(
                    35,
                    20,
                    70,
                    Level.NORMAl
                )
            }
            Level.HARD -> {
                return GameSettings(
                    35,
                    25,
                    90,
                    Level.HARD
                )
            }
        }
        return throw RuntimeException("GameSetting isn't init")
    }

    fun generateQuestion(): Question {
        val range = when (gameSettings.level) {
            Level.TEST -> {
                TEST
            }
            Level.EASY -> {
                EASY
            }
            Level.NORMAl -> {
                NORMAL
            }
            Level.HARD -> {
                HARD
            }
        }

        val sum = (Random().nextInt(range) + MIN_VALUE) * 2
        var visibleNumber =
            (sum + MIN_VALUE) - Random().nextInt(sum - MIN_VALUE)
        while (visibleNumber < MIN_VALUE) {
            visibleNumber++
        }
        val solution = sum - visibleNumber
        val list = hashSetOf<Int>()
        list.add(solution)

        for (it in solution until solution + 3) {
            list.add(it)
        }
        for (it in solution downTo solution - 3) {
            list.add(it)
        }
        return Question(
            sum = sum,
            visibleNumber = visibleNumber,
            solution = solution,
            listOfVariants = list.toMutableList().apply { shuffle() }
        )
    }

    fun shareGamesettings(): GameSettings {
        return gameSettings
    }

    companion object {
        fun build(
            level: OnUserResponceUseCase.Responce.Setting
        ): QuestionGenerator {
            return QuestionGenerator(level)
        }

         
        private const val MIN_VALUE = 2
        private const val TEST = 5
        private const val EASY = 8
        private const val NORMAL = 15
        private const val HARD = 20
    }

}


