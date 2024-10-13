package com.example.design.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteComponent(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    Box {
        IconButton(onClick = { onFavoriteClick() }) {
            Icon(
                if (isFavorite.or(false)) Icons.Filled.Favorite else Icons.Outlined.Favorite,
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