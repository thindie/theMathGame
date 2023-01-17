package com.example.thindie.themathgame.presentation.theme.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.GameResults


@Composable
fun ShowWinners(list: List<GameResults>, onClickBack: () -> Unit) {


    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ElevatedCard(
            shape = ShapeDefaults.ExtraLarge,
            modifier = Modifier
                .padding(all = 2.dp)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 1.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOP PERFORMANCE!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "below:",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .padding(all = 15.dp),
                    contentPadding = PaddingValues(bottom = 1.dp)
                ) {
                    items(list) { item ->
                        WinnerCard(gameResults = item)
                    }

                }

                IconButton(
                    onClick = {
                        onClickBack()

                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp, 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )

                }
            }


        }
    }

}
