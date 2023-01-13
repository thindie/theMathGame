package com.example.thindie.themathgame.presentation.theme.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GamingPlace(){
    Surface(Modifier.fillMaxSize()) {
        Text(text = "Game")
    }
}