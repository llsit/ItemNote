package com.example.itemnote.screen.shop

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.itemnote.component.AddShopBottomSheet
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.Loading
import com.example.itemnote.component.MediumToolbarComponent
import com.example.itemnote.component.ShopCard
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.utils.UiState

@Composable
fun ShopListScreen(
    navController: NavHostController,
    shopListViewModel: ShopListViewModel = hiltViewModel()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = shopListViewModel.uiStateGetShop.collectAsState()
    val selectedShopModel by shopListViewModel.selectedItem.collectAsState()
    LaunchedEffect(Unit) {
        shopListViewModel.getShop()
    }
    Scaffold(
        topBar = {
            MediumToolbarComponent(title = "Shop List", true) {
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
        when (state.value) {
            is UiState.Error -> {
                Loading(isLoading = false)
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
            }

            UiState.Idle -> Unit
            UiState.Loading -> Loading(isLoading = true)
            is UiState.Success -> {
                Loading(isLoading = false)
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (state.value as UiState.Success<List<ShopModel>>).data?.let {
                        itemsIndexed(it) { index, item ->
                            ShopCard(Modifier, item, index)
                        }
                    }
                }
            }
        }

        if (showBottomSheet) {
            AddShopBottomSheet(scope = scope) {
                showBottomSheet = it
                shopListViewModel.getShop()
            }
        }
    }
}
