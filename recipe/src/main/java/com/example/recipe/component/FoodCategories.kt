package com.example.recipe.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FoodCategories() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        HeaderMedium(text = "Categories")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(
                "Chicken",
                com.google.firebase.appcheck.interop.R.drawable.common_full_open_on_phone,
                Color(0xFFE0F7FA)
            )
            FoodCategoryItem(
                "Beef",
                com.google.android.gms.base.R.drawable.common_full_open_on_phone,
                Color(0xFFFFF8E1)
            )
            FoodCategoryItem(
                "Fish",
                com.google.android.gms.base.R.drawable.common_full_open_on_phone,
                Color(0xFFF3E5F5)
            )
            FoodCategoryItem(
                "Bakery",
                com.google.android.gms.base.R.drawable.common_full_open_on_phone,
                Color(0xFFFFEBEE)
            )
        }
    }

}

@Composable
fun FoodCategoryItem(name: String, iconRes: Int, backgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name)
    }
}