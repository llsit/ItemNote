package com.example.recipe.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipe.component.HeaderLarge

@Composable
fun RecipeDetailScreen() {
    Column(modifier = Modifier.padding(8.dp)) {
        HeaderLarge(text = "Detail")
    }

}