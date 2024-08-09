package com.example.itemnote.screen.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.itemnote.SharedViewModel
import com.example.itemnote.component.ChipGroupHorizontalList
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.ItemComponent
import com.example.itemnote.component.Loading
import com.example.itemnote.component.ProfileMenuComponent
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.screen.user.UserViewModel
import com.example.itemnote.usecase.CategoryModel
import com.example.itemnote.utils.AuthState
import com.example.itemnote.utils.Constants.Category.HOME
import com.example.itemnote.utils.NavigationItem
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val authState by mainViewModel.authState.collectAsState()
    val category by mainViewModel.uiStateCategory.collectAsState()

    when (authState) {
        AuthState.Loading -> Loading()
        AuthState.Unauthenticated -> {
            navController.navigate(NavigationItem.Login.route)
        }

        else -> Unit
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ProfileMenuComponent(
                    name = userViewModel.name.value,
                    email = userViewModel.email.value
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(NavigationItem.Main.route)
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.logout()
                            navController.navigate(NavigationItem.Login.route)
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                ToolbarScreen(title = "Home", false, onManuClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            },
            floatingActionButton = {
                FloatingButton {
                    navController.navigate(NavigationItem.AddItem.route)
                }
            }
        )
        { innerPadding ->
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
                val state = mainViewModel.uiState.collectAsState()
                when (state.value) {
                    is UiState.Error -> {
                        val error = (state.value as UiState.Error).message
                        Toast.makeText(LocalContext.current, "Error : ${error}", Toast.LENGTH_LONG)
                            .show()
                    }

                    UiState.Idle -> Unit
                    UiState.Loading -> {
                        Loading()
                    }

                    is UiState.Success -> {
                        ItemListSection(
                            itemList = (state.value as UiState.Success<List<ItemModel>>).data,
                            onClickListener = {
                                sharedViewModel.updateSelectedItemModel(it)
                                navController.navigate("shopList/${it.id}")
                            }
                        )
                    }
                }
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
        itemList?.let { item ->
            items(item) {
                ItemComponent(
                    modifier = Modifier,
                    name = it.name,
                    price = it.shop?.price.toString(),
                    location = it.shop?.location.orEmpty(),
                    locationName = it.shop?.name.orEmpty(),
                    imageUrl = it.imageUrl
                ) {
                    onClickListener(it)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ItemListSectionPreview() {
    ItemListSection(
        itemList = listOf(
            ItemModel(
                id = "1",
                name = "Item 1",
                imageUrl = "",
                shop = ShopModel(
                    id = "1",
                    name = "Shop 1",
                    location = "Location 1",
                    price = 10.0
                ),
                categoryModel = CategoryModel(
                    id = "1",
                    name = "Category 1"
                )
            ),
            ItemModel(
                id = "2",
                name = "Item 2",
                imageUrl = "",
                shop = null,
                categoryModel = CategoryModel(
                    id = "2",
                    name = "Category 2"
                )
            )
        ),
        onClickListener = {}
    )
}

