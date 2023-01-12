package com.example.thindie.presentation.ui.composables

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun Place(listOfVariants: List<Int>?, onClick: () -> Unit) {
    Surface {
         if(listOfVariants.isNullOrEmpty()){
             listOf<String>("TEST", "ЛЕГКО", "НОРМАЛЬНО", "СЛОЖНО").forEach {
                 Element(it)
             }
         }
    }
}