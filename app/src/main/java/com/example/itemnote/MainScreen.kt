package com.example.itemnote

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.itemnote.component.CardItem
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.Loading
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.utils.UiState

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val stateScroll = rememberScrollState()
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Home", false)
        },
        floatingActionButton = {
            FloatingButton {
                navController.navigate("addItem")
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
                text = "Hello Android!"
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
                                CardItem(Modifier.padding(5.dp), text = it.name) {
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
