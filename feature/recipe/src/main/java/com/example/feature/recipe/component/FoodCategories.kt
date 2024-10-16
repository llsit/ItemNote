package com.example.feature.recipe.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.design.R
import com.example.core.model.data.RecipeCategoryModel
import com.example.design.ui.HeaderMedium

@Composable
fun FoodCategories(categories: List<RecipeCategoryModel>, onClickItem: (String) -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        HeaderMedium(text = "Categories")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) {
                FoodCategoryItem(
                    name = it.strCategory,
                    iconUrl = it.strCategoryThumb,
                    backgroundColor = Color.LightGray,
                    onClickItem = onClickItem
                )
            }
        }
    }

}

@Composable
fun FoodCategoryItem(
    name: String,
    iconUrl: String,
    backgroundColor: Color,
    onClickItem: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClickItem(name) }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (iconUrl.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_product),
                    contentDescription = name,
                    modifier = Modifier.size(40.dp)
                )
            } else {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = name,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name)
    }
}

@Composable
@Preview(showBackground = true)
fun FoodCategoriesPreview() {
    FoodCategories(
        categories = listOf(
            RecipeCategoryModel(
                idCategory = "",
                strCategory = "Category 1",
                strCategoryThumb = "",
                strCategoryDescription = ""
            ),
            RecipeCategoryModel(
                idCategory = "",
                strCategory = "Category 2",
                strCategoryThumb = "",
                strCategoryDescription = ""
            )
        ),
    )
}