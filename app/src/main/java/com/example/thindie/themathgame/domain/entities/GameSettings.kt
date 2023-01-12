package com.example.thindie.themathgame.domain.entities


data class GameSettings(
    val playingTime: Int,
    val solvedQuestions: Int,
    val percentage: Int,
    val level: Level,
    private val VARIANTS_OF_ANSWERS: Int = 6
)
