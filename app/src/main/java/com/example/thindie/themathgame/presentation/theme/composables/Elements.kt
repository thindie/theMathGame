package com.example.thindie.presentation.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun Element(header: String) {
    val modifier = Modifier.padding(all = 50.dp).size(50.dp).clip(CircleShape)
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.onPrimaryContainer) {
        Text(text = header, style = MaterialTheme.typography.bodyLarge)
    }
}

