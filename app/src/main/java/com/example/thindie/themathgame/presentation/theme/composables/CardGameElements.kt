package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.thindie.themathgame.domain.entities.Level
import com.example.thindie.themathgame.presentation.theme.Shapes


@Composable
fun LoadElement(
    header: String,
    padding: PaddingValues,
    onClick: (level: Level) -> Unit,
    level: Level
) {
    val interactionSource = MutableInteractionSource()
    val modifier = Modifier
        .size(160.dp)
        .padding(padding)
    ElevatedCard(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick(level) }, shape = CircleShape
    ) {
        Box(
            Modifier
                .padding(12.dp)
                .align(CenterHorizontally)

        ) {
            Text(
                text = header,
                Modifier.padding(top = 35.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }
}

@Composable
fun GameElement(
    answer: Int,
    variant: Int,
    padding: PaddingValues,
    onClick: (Boolean) -> Unit,

    ) {


    val modifier = Modifier
        .size(130.dp)
        .padding(padding)
    ElevatedCard(
        modifier = modifier.clickable { onClick(answer == variant) },
        shape = Shapes.extraLarge
    ) {
        Box(
            Modifier
                .padding(12.dp)
                .align(CenterHorizontally)

        ) {
            Text(
                text = variant.toString(),
                color = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.padding(top = 35.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }


    }
}

@Composable
fun VisionElement(
    header: String,
    padding: PaddingValues,

    ) {

    val modifier = Modifier
        .size(170.dp)
        .padding(padding)
    ElevatedCard(
        modifier = modifier.scale(1.2f),
        shape = CircleShape
    ) {
        Box(
            Modifier
                .padding(12.dp)
                .align(CenterHorizontally)

        ) {
            Text(
                text = header,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 35.dp),
                style = MaterialTheme.typography.headlineLarge
            )
        }


    }
}