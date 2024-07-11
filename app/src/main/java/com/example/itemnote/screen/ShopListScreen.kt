package com.example.itemnote.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.itemnote.component.AddShopBottomSheet
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.ShopCard
import com.example.itemnote.component.ToolbarScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(navController: NavHostController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Shop List", true) {
                navController.popBackStack()
            }
        },
        floatingActionButton = {
            FloatingButton {
                showBottomSheet = true
            }
        }
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                ShopCard(Modifier, "$it") {

                }
            }
        }

        if (showBottomSheet) {
            AddShopBottomSheet(scope = scope, sheetState = sheetState) {
                showBottomSheet = it
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewShopListScreen() {
//    ShopListScreen(navController)
//}