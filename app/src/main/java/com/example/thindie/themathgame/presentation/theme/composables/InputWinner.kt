package com.example.thindie.themathgame.presentation.theme.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.GameResults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputWinner(onHoistClick: (GameResults) -> Unit, gameResults: GameResults) {
    val styleHead = MaterialTheme.typography.headlineLarge
    val styleDetails = MaterialTheme.typography.headlineSmall

    val text = remember {
        mutableStateOf("")
    }


    Surface(color = MaterialTheme.colorScheme.surface) {
        ElevatedCard(shape = ShapeDefaults.ExtraLarge, modifier = Modifier.padding(all = 20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 15.dp)
            ) {
                Text(
                    text = String.format("Congrats! We are the Champions!"),
                    style = styleHead,
                    color = MaterialTheme.colorScheme.surfaceTint
                )
                Text(
                    text = String.format("please name yourself"),
                    style = styleDetails
                )

                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onHoistClick.invoke(gameResults.copy(
                                    name = text.value
                                ))
                            }
                        )
                    },

                    supportingText = { Text(text = "enter your name") },
                    modifier = Modifier.padding(all = 20.dp),
                    maxLines = 1,
                )


            }
        }

    }
}

