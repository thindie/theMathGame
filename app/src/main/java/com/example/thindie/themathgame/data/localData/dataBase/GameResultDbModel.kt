package com.example.thindie.themathgame.data.localData.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class GameResultDbModel(
    val name: String,
    val solvedQuestions: Int,
    val totalQuestions: Int,
    @PrimaryKey
    val gameScore: Int,
    val winRate: Int
)