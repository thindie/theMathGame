package com.example.thindie.presentation.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.presentation.theme.MathGameTheme
import com.example.thindie.themathgame.presentation.theme.composables.GamingPlace
import com.example.thindie.themathgame.presentation.theme.composables.LoadingPlace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Place(listOfVariants: List<Int>?, onLevelClick: (level: Level) -> Unit) {
    if(listOfVariants.isNullOrEmpty()){
        LoadingPlace( onLevelClick )
    }
    else{
        GamingPlace()
    }

}

/*@Preview
@Composable
fun PreviewPlace() {
    MathGameTheme() {
        Place(null) { println() }
    }
}*/

