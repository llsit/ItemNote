package com.example.itemnote.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itemnote.usecase.CategoryModel

@Composable
fun ChipComponent(
    text: String,
    selectedId: String,
    onSelected: (String) -> Unit
) {
    FilterChip(
        selected = text == selectedId,
        onClick = { onSelected(if (text == selectedId) "" else text) },
        label = { Text(text) }
    )
}

@Composable
fun ChipGroupList(categoryList: List<CategoryModel>?, onSelected: (CategoryModel?) -> Unit) {
    var selectedCategoryId by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categoryList?.forEach { category ->
            ChipComponent(
                text = category.name,
                selectedId = selectedCategoryId,
                onSelected = { newSelection ->
                    selectedCategoryId = newSelection
                    onSelected(
                        if (newSelection.isEmpty()) {
                            null
                        } else {
                            category
                        }
                    )
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
