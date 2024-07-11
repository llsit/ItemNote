package com.example.itemnote.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardItem(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth(),
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Title : $text",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Place : $text",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Price : $text",
            fontSize = 12.sp,
            modifier = modifier.padding(6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardItem() {
    CardItem(text = "Item"){

    }
}