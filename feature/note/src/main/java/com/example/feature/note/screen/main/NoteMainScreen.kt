package com.example.feature.note.screen.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.common.utils.Constants.Category.HOME
import com.example.core.data.utils.SharedViewModel
import com.example.core.common.utils.UiState
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import com.example.design.ui.Loading
import com.example.feature.note.component.ChipGroupHorizontalList
import com.example.feature.note.component.ItemComponent
import com.example.feature.note.screen.user.UserViewModel

@Composable
fun NoteMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    innerPadding: PaddingValues
) {
    val category by mainViewModel.uiStateCategory.collectAsState()
    val state by mainViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            text = "Hello ${userViewModel.name.value}!",
            style = MaterialTheme.typography.titleLarge
        )
        when (category) {
            is UiState.Success -> {
                (category as UiState.Success<List<CategoryModel>>).data?.let { categoryList ->
                    val list = listOf(
                        CategoryModel("home", HOME)
                    ).plus(categoryList)
                    ChipGroupHorizontalList(list) { selectedCategory ->
                        mainViewModel.getItemsByCategory(selectedCategory?.id.orEmpty())
                    }
                }
            }

            is UiState.Loading -> Loading()
            else -> Unit
        }

        when (state) {
            is UiState.Error -> {
                val error = (state as UiState.Error).message
                Toast.makeText(LocalContext.current, "Error : ${error}", Toast.LENGTH_LONG)
                    .show()
            }

            UiState.Idle -> Unit
            UiState.Loading -> {
                Loading()
            }

            is UiState.Success -> {
                ItemListSection(
                    itemList = (state as UiState.Success<List<ItemModel>>).data,
                    onClickListener = {
                        sharedViewModel.updateSelectedItemModel(it)
                        navController.navigate("shopList/${it.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun ItemListSection(
    itemList: List<ItemModel>?,
    onClickListener: (ItemModel) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        itemList?.let { items ->
            itemsIndexed(items) { index, item ->
                ItemComponent(
                    modifier = Modifier,
                    name = item.name,
                    price = item.shop?.price.toString(),
                    location = item.shop?.location.orEmpty(),
                    locationName = item.shop?.name.orEmpty(),
                    imageUrl = item.imageUrl
                ) {
                    onClickListener(item)
                }
            }
        }
    }
}
