package com.example.design.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteComponent(
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit = {}
) {
    val favorite by remember { mutableStateOf(isFavorite) }
    Box {
        IconButton(onClick = { onFavoriteClick(favorite) }) {
            Icon(
                if (favorite.or(false)) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteComponentPreview() {
    FavoriteComponent(isFavorite = true)
}