package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.presentation.MainViewModel

@Composable
fun GamingPlace(
    question: Question,
    viewModel: MainViewModel,
    onClick: (Long?) -> Unit
) {

    val toShowScores = viewModel
        .resultState
        .collectAsState()
        .value.gameResults
        .gameScore

    val ticks = viewModel.timerState
        .collectAsState()
        .value


    val modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)


    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = modifier) {
                Column() {
                    Text(text = ticks)
                    Spacer(modifier = Modifier.weight(0.3f))
                    Row() {
                        Spacer(modifier = Modifier.weight(0.1f))
                        VisionElement(
                            header = question.sum.toString(),
                            padding = PaddingValues(10.dp)
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                    }
                    Row() {
                        VisionElement(
                            header = question.visibleNumber.toString(),
                            padding = PaddingValues(30.dp)
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                        Text(text = "Score : ".plus(toShowScores), Modifier.padding(top = 10.dp))
                        Spacer(modifier = Modifier.weight(0.1f))
                        VisionElement(
                            header = "?",
                            padding = PaddingValues(30.dp),

                            )
                    }
                }
            }


            Box(modifier = modifier) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 120.dp), content = {
                    items(
                        items = question.listOfVariants
                    ) { item ->
                        GameElement(
                            answer = question.solution,
                            variant = item,
                            padding = PaddingValues(all = 5.dp),
                            time = question.timeSpendOn,
                            onClick = onClick
                        )
                    }

                }
                )

            }


        }
    }
}



