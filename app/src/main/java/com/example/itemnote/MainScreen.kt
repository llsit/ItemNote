package com.example.itemnote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.itemnote.component.CardItem
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.ui.theme.ItemNoteTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
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
                    CardItem(modifier.padding(5.dp), text = "Item: $index") {
                        navController.navigate("shopList")
                    }
                }
            }
        }
    }
}
