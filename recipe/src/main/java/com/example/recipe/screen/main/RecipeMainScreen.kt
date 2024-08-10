package com.example.recipe.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecipeMainScreen(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = "Hello Recipe")
    }
}