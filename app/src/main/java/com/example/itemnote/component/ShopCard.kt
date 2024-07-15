package com.example.itemnote.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itemnote.data.model.ShopModel

@Composable
fun ShopCard(modifier: Modifier = Modifier, model: ShopModel) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            fontSize = 16.sp,
            text = "Shop : ${model.name}"
        )
        Text(
            modifier = modifier.padding(4.dp),
            fontSize = 12.sp,
            text = "Price : ${model.price}"
        )
        Text(
            modifier = modifier.padding(4.dp),
            fontSize = 12.sp,
            text = "Location : ${model.location}"
        )
    }
}