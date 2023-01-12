package com.example.thindie.themathgame.domain.entities


data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val solution: Int,
    val listOfVariants: List<Int>
)




