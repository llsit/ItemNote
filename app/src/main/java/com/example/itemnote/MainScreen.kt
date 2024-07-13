package com.example.itemnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.itemnote.component.CardItem
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.ToolbarScreen

@Composable
fun MainScreen(navController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                text = "Hello Android!"
            )
            LazyColumn() {
                items(5) { index ->
                    CardItem(Modifier.padding(5.dp), text = "Item: $index") {
                        navController.navigate("shopList")
                    }
                }
            }
        }
    }
}
