package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.GameResults

@Composable
fun WinnerCard(gameResults: GameResults) {
    val styleHead = MaterialTheme.typography.headlineLarge
    val styleDetails = MaterialTheme.typography.headlineSmall
    val styleLil = MaterialTheme.typography.bodyMedium
    val mod = Modifier.padding(start = 20.dp)


    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = ShapeDefaults.ExtraLarge) {
        Column(
            modifier = Modifier.fillMaxWidth(). padding(all= 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            gameResults.name?.let { Text(text = it, style = styleHead) }
            Text(
                text = gameResults.gameScore.toString().plus(" scores"),
                style = styleDetails,
                color = MaterialTheme.colorScheme.surfaceTint,
                modifier = mod
            )
            gameResults.solvedQuestions?.let {
                Text(
                    text = "Solved Questions ".plus(it.toString()),
                    style = styleLil,
                    modifier = mod
                )
            }
            gameResults.totalQuestions?.let {
                Text(
                    text = "Total Questions ".plus(it.toString()),
                    style = styleLil,
                    modifier = mod
                )
            }
            gameResults.winRate?.let {
                Text(
                    text = "Win Rate in % ".plus(it.toString()),
                    style = styleLil,
                    modifier = mod
                )
            }
        }


    }
}