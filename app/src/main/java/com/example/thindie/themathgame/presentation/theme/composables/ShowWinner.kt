package com.example.thindie.themathgame.presentation.theme.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.GameResults


@Composable
fun ShowWinners(list: List<GameResults>, onClickBack: () -> Unit) {



    Surface(color = MaterialTheme.colorScheme.surface) {
        ElevatedCard(shape = ShapeDefaults.ExtraLarge, modifier = Modifier.padding(all = 20.dp)) {
            Column(modifier = Modifier.padding(all = 5.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .padding(all = 15.dp),
                    contentPadding = PaddingValues(all = 4.dp)
                ) {
                    items(list) { item ->
                        WinnerCard(gameResults = item)
                    }

                }
                Spacer(modifier = Modifier.weight(0.2f))
                IconButton(
                    onClick = {
                        onClickBack()

                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .size(80.dp, 60.dp)
                ) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            }


        }
    }

}
