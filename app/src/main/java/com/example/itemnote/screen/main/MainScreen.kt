package com.example.itemnote.screen.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.itemnote.component.CardItem
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.Loading
import com.example.itemnote.component.ProfileMenuComponent
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.screen.user.UserViewModel
import com.example.itemnote.utils.AuthState
import com.example.itemnote.utils.NavigationItem
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val authState = mainViewModel.authState.collectAsState()

    when (authState.value) {
        AuthState.Authenticated -> Unit
        AuthState.Initial -> Unit
        AuthState.Loading -> Loading(isLoading = true)
        AuthState.Unauthenticated -> {
            navController.navigate(NavigationItem.Login.route)
        }
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
            LaunchedEffect(Unit) {
                mainViewModel.getItems()
            }
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "Hello ${userViewModel.name.value}!"
                )
                val state = mainViewModel.uiState.collectAsState()
                when (state.value) {
                    is UiState.Error -> {
                        Loading(isLoading = false)
                        val error = (state.value as UiState.Error).message
                        Toast.makeText(LocalContext.current, "Error : ${error}", Toast.LENGTH_LONG)
                            .show()
                    }

                    UiState.Idle -> Loading(isLoading = false)
                    UiState.Loading -> {
                        Loading(isLoading = true)
                    }

                    is UiState.Success -> {
                        Loading(isLoading = false)
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            (state.value as UiState.Success<List<ItemModel>>).data?.let {
                                items(it) {
                                    CardItem(
                                        Modifier.padding(5.dp),
                                        name = it.name,
                                        price = it.shop?.price.toString(),
                                        location = it.shop?.location.orEmpty(),
                                        locationName = it.shop?.name.orEmpty(),
                                        imageUrl = it.imageUrl
                                    ) {
                                        navController.navigate("shopList/${it.id}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

