package com.example.thindie.presentation.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.presentation.theme.composables.LoadingPlace


@Composable
fun Place(listOfVariants: List<Int>?, onLevelClick: (level: Level) -> Unit, onWinner:  () -> Unit) {
    if (listOfVariants.isNullOrEmpty()) {
        LoadingPlace(onLevelClick, onWinner)
    }

}
