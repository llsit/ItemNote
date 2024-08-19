package com.example.feature.note.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.common.navigation.NavigationItem
import com.example.core.data.utils.AuthState
import com.example.core.data.utils.SharedViewModel
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import com.example.core.model.data.ShopModel
import com.example.design.ui.Loading
import com.example.design.ui.ToolbarScreen
import com.example.feature.note.component.FloatingButton
import com.example.feature.note.component.ProfileMenuComponent
import com.example.feature.note.screen.user.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    scope: CoroutineScope = rememberCoroutineScope(),
    onNavigateToRecipe: () -> Unit = {}
) {
    val authState by mainViewModel.authState.collectAsState()

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
            ModalDrawerSheetSection(
                drawerState = drawerState,
                scope = scope,
                name = userViewModel.name.value,
                email = userViewModel.email.value,
                onNavigateToHome = {
                    navController.navigate(NavigationItem.Main.route)
                },
                onNavigateToRecipe = onNavigateToRecipe,
                onLogout = {
                    mainViewModel.logout()
                    navController.navigate(NavigationItem.Login.route)
                }
            )
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
            NoteMainScreen(
                innerPadding = innerPadding,
                navController = navController,
                mainViewModel = mainViewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@Composable
fun ModalDrawerSheetSection(
    drawerState: DrawerState,
    scope: CoroutineScope,
    name: String,
    email: String,
    onNavigateToHome: () -> Unit,
    onNavigateToRecipe: () -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet {
        ProfileMenuComponent(
            name = name,
            email = email
        )
        HorizontalDivider()
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                    onNavigateToHome()
                }
            }
        )
        NavigationDrawerItem(
            icon = {
                Icon(
                    Icons.Default.FormatListNumbered,
                    contentDescription = "Recipe"
                )
            },
            label = { Text("Recipe") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                    // Navigate to recipe
                    onNavigateToRecipe()
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
                    onLogout()
                }
            }
        )
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

