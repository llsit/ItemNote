package com.example.recipe.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe.component.HeaderLarge

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
        val padding = innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderLarge(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "What recipe are you looking for?"
            )

            SearchSection(
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            FoodCategories()
        }
    }

}

@Composable
fun SearchSection(modifier: Modifier) {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = modifier.height(56.dp),
        placeholder = { Text("Search for your query") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.1f)
        ),
        singleLine = true
    )
}

@Composable
fun FoodCategories() {
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

@Composable
@Preview(showBackground = true)
fun RecipeMainScreenPreview() {
    RecipeMainScreen()
}