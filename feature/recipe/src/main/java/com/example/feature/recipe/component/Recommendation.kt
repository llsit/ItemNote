package com.example.feature.recipe.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.design.R
import com.example.core.model.data.RecommendationModel
import com.example.design.ui.FavoriteComponent
import com.example.design.ui.HeaderMedium

@Composable
fun RecommendationList(
    recommendRecipes: List<RecommendationModel>,
    onClick: (String) -> Unit = {},
    onFavoriteClick: (String, Boolean) -> Unit
) {
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
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendRecipes) {
                RecommendationCard(
                    mealThumb = it.mealThumb,
                    title = it.title,
                    category = it.category,
                    isFavorite = it.isFavorite,
                    onClick = {
                        onClick(it.id)
                    },
                    onFavoriteClick = { isFav ->
                        onFavoriteClick(it.id, isFav)
                    }
                )
            }
        }
    }
}

@Composable
fun RecommendationCard(
    mealThumb: String,
    title: String,
    category: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onFavoriteClick: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable { onClick() },
    ) {
        AsyncImage(
            model = mealThumb,
            contentDescription = title,
            placeholder = painterResource(id = R.drawable.placeholder_product),
            error = painterResource(id = R.drawable.placeholder_product),
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            FavoriteComponent(
                isFavorite = isFavorite,
                onFavoriteClick = onFavoriteClick
            )
        }
        Text(
            text = "Category $category",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationCardPreview() {
    RecommendationCard(
        mealThumb = "",
        title = "Title",
        category = "Category",
        isFavorite = false
    )
}
