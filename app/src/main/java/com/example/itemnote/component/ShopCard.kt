package com.example.itemnote.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShopCard(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = {
            onClick()
        }
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            text = "shop : $title"
        )
        Text(
            modifier = modifier.padding(8.dp),
            text = "price : $title"
        )
    }
}