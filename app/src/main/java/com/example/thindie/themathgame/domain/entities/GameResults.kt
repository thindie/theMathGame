package com.example.thindie.themathgame.domain.entities

import com.example.thindie.themathgame.domain.entities.GameSettings


data class GameResults(
    val solvedQuestions: Int,
    val totalQuestions: Int,
    val isWinner: Boolean,
    val gameSettings: GameSettings,
    val winRate: Int
)

