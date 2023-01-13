package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.presentation.theme.MathGameTheme


@Composable
fun LoadElement(header: String, padding: PaddingValues, onClick: (level: Level) -> Unit, level: Level) {
    val modifier = Modifier
        .size(160.dp)
        .padding(padding)
    Card(modifier = modifier.clickable { onClick(level) }, shape = CircleShape) {
        Box(
            Modifier
                .padding(12.dp)
                .align(CenterHorizontally)

        ) {
            Text(
                text = header,
                Modifier.padding(top = 35.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }


    }
}


