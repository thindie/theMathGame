package com.example.thindie.themathgame.presentation.theme.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.thindie.themathgame.domain.entities.GameResults


@Composable
fun ShowGameResult(list: List<GameResults>, onClickBack: () -> Unit, onClickAll: () -> Unit) {
    val result = list[0]
    val styleHead = MaterialTheme.typography.headlineLarge
    val styleDetails = MaterialTheme.typography.headlineSmall
    val styleLil = MaterialTheme.typography.bodyMedium

    Surface(color = MaterialTheme.colorScheme.surface) {
        ElevatedCard(shape = ShapeDefaults.ExtraLarge, modifier = Modifier.padding(all = 20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 15.dp)
            ) {
                Text(
                    text = String.format("Game Score is : %d", result.gameScore),
                    style = styleHead,
                    color = MaterialTheme.colorScheme.surfaceTint
                )
                Text(
                    text = String.format("Answered questions is : %d", result.solvedQuestions),
                    style = styleDetails
                )
                Text(
                    text = String.format("Total questions is : %d", result.totalQuestions),
                    style = styleDetails
                )

                Text(
                    text = String.format("WinRate is : %d", result.winRate).plus("%"),
                    style = styleDetails
                )

                Text(
                    text = String.format("game time is 25 seconds ;)"),
                    style = styleLil
                )

                Spacer(modifier = Modifier.weight(1f, true))
                Row() {
                    IconButton(
                        onClick = { onClickBack() },
                        modifier = Modifier
                            .padding(20.dp)
                            .size(20.dp, 60.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            try {
                                onClickAll()
                            } catch (e: java.lang.IndexOutOfBoundsException) {
                                onClickBack()
                            }
                        },
                        modifier = Modifier
                            .padding(20.dp)
                            .size(80.dp, 60.dp)
                    ) {
                        Row() {
                            Text(text = "winners", modifier = Modifier.padding(end = 3.dp))
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "AllPeople"
                            )
                        }

                    }
                }


            }
        }

    }
}