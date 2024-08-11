package com.example.recipe.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.component.FoodCategories
import com.example.recipe.component.HeaderLarge
import com.example.recipe.component.HeaderMedium
import com.example.recipe.component.RecipeList
import com.example.recipe.component.SearchSection

@Composable
fun RecipeMainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Search", "Favorite")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Favorite)

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {

        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeaderLarge(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "What recipe are you looking for?"
            )

            SearchSection(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {}
            )
            FoodCategories()
            RecommendationList()

            RecipeList()
        }
    }
}

@Composable
fun RecommendationList() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderMedium(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Recommendation"
            )
            Text(
                text = "See all",
                fontSize = 14.sp,
                color = Color.Green,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(3) { // Adjust the number of items
                RecommendationCard(
                    imageRes = com.google.firebase.appcheck.interop.R.drawable.common_full_open_on_phone, // Replace with your image resource
                    title = "Creamy Pasta",
                    author = "David Charles"
                )
                RecommendationCard(
                    imageRes = com.google.android.gms.base.R.drawable.common_full_open_on_phone, // Replace with your image resource
                    title = "Macarons",
                    author = "Rachel William"
                )
                RecommendationCard(
                    imageRes = com.google.android.gms.base.R.drawable.common_full_open_on_phone, // Replace with your image resource
                    title = "Chicken Dish",
                    author = "Samantha Lee"
                )
            }
        }
    }
}


@Composable
fun RecommendationCard(
    imageRes: Int,
    title: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "By $author",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
@Preview(showBackground = true)
fun RecipeMainScreenPreview() {
    RecipeMainScreen()
}