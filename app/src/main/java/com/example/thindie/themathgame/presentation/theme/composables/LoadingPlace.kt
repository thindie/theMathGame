package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.Level

@Composable
fun LoadingPlace(onClick: (level: Level) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {

        Row(horizontalArrangement = Arrangement.End) {
            Spacer(modifier = Modifier.weight(0.1f))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)

            ) {
                Spacer(modifier = Modifier.weight(0.4f))
                LoadElement(header = "TEST", padding = PaddingValues(20.dp), onClick, Level.TEST)
                LoadElement(header = "ЛЕГКО", padding = PaddingValues(20.dp), onClick, Level.EASY)
                Spacer(modifier = Modifier.weight(0.4f))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)
            ) {
                Spacer(modifier = Modifier.weight(0.4f))
                LoadElement(
                    header = "НОРМАЛЬНО",
                    padding = PaddingValues(20.dp),
                    onClick,
                    Level.NORMAl
                )
                LoadElement(header = "СЛОЖНО", padding = PaddingValues(20.dp), onClick, Level.HARD)
                Spacer(modifier = Modifier.weight(0.4f))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }


    }
}