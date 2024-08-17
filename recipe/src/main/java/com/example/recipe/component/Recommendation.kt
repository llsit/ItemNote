package com.example.recipe.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendationList() {
    val list = listOf<RecommendationModel>(
//        RecommendationModel(
//            imageRes = com.google.android.material.R.drawable.abc_ic_star_half_black_48dp,
//            title = "Creamy Pasta",
//            author = "David Charles"
//        ),
//        RecommendationModel(
//            imageRes = com.google.android.material.R.drawable.abc_ic_star_half_black_48dp,
//            title = "Macarons",
//            author = "Rachel William"
//        ),
//        RecommendationModel(
//            imageRes = com.google.android.material.R.drawable.abc_ic_star_half_black_48dp,
//            title = "Chicken Dish",
//            author = "Samantha Lee"
//        )
    )
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
        Row(modifier = Modifier.fillMaxWidth()) {
            list.forEach {
                RecommendationCard(
                    imageRes = it.imageRes,
                    title = it.title,
                    author = it.author
                )
            }
        }
    }
}

data class RecommendationModel(
    val imageRes: Int,
    val title: String,
    val author: String,
)

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