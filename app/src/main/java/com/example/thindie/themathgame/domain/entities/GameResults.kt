package com.example.thindie.themathgame.domain.entities


data class GameResults(
    val solvedQuestions: Int?,
    val totalQuestions: Int?,
    val isWinner: Boolean?,
    val gameScore: Int,
    val winRate: Int?
)

