package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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



    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(all = 20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)

            ) {
                VisionElement(
                    header = question.sum.toString(),
                    padding = PaddingValues(10.dp)
                )
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
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = ticks, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.weight(0.3f))
            Row() {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(all = 2.dp),
                    content = {
                        items(
                            items = question.listOfVariants
                        ) { item ->
                            GameElement(
                                answer = question.solution,
                                variant = item,
                                padding = PaddingValues(all = 3.dp),
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














