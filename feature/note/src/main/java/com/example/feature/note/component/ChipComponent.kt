package com.example.feature.note.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.model.data.CategoryModel

@Composable
fun ChipComponent(
    text: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = { onSelected(!selected) },
        label = { Text(text) }
    )
}

@Composable
fun ChipGroupList(
    categoryList: List<CategoryModel>?,
    onSelected: (CategoryModel?) -> Unit,
    selectedId: String = ""
) {
    var selectedCategoryId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (selectedId.isNotEmpty()) {
            selectedCategoryId = selectedId
            onSelected(categoryList?.firstOrNull { it.id == selectedId })
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categoryList?.forEach { category ->
            ChipComponent(
                text = category.name,
                selected = category.id == selectedCategoryId,
                onSelected = { isSelected ->
                    selectedCategoryId = if (isSelected) category.id else ""
                    onSelected(if (isSelected) category else null)
                }
            )
        }
    }
}

@Composable
fun ChipGroupHorizontalList(
    categoryList: List<CategoryModel>?,
    onSelected: (CategoryModel?) -> Unit
) {
    var selectedCategoryId by remember { mutableStateOf("") }

    LaunchedEffect(categoryList) {
        categoryList?.firstOrNull()?.let { firstCategory ->
            selectedCategoryId = firstCategory.id // Assuming CategoryModel has an 'id' field
            onSelected(firstCategory)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categoryList?.forEach { category ->
            ChipComponent(
                text = category.name,
                selected = category.id == selectedCategoryId,
                onSelected = { selected ->
                    selectedCategoryId = if (selected) category.id else ""
                    onSelected(if (selected) category else null)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelComponent() {
    ChipGroupList(
        categoryList = listOf(
            CategoryModel("Category 1", ""),
            CategoryModel("Category 2", ""),
            CategoryModel("Category 3", ""),
            CategoryModel("Category 4", ""),
            CategoryModel("Category 5", ""),
        ),
        {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelList() {
    ChipGroupHorizontalList(
        categoryList = listOf(
            CategoryModel("Category 1", ""),
            CategoryModel("Category 2", ""),
            CategoryModel("Category 3", ""),
            CategoryModel("Category 4", ""),
            CategoryModel("Category 5", ""),
        ),
        {}
    )
}
