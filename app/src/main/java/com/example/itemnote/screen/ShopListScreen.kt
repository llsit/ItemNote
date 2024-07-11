package com.example.itemnote.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.ShopCard
import com.example.itemnote.component.ToolbarScreen

@Composable
fun ShopListScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Shop List", true){
                navController.popBackStack()
            }
        },
        floatingActionButton = {
            FloatingButton {

            }
        }
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(5) {
                ShopCard(Modifier, "$it") {

                }
            }
        }

    }
}

//@Preview
//@Composable
//fun PreviewShopListScreen() {
//    ShopListScreen(navController)
//}