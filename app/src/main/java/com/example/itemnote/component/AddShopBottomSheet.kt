package com.example.itemnote.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itemnote.screen.shop.ShopListViewModel
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShopBottomSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    idItem: String? = "",
    viewModel: ShopListViewModel = hiltViewModel(),
    onClick: (Boolean) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val state = viewModel.uiStateAddShop.collectAsState()
    when (state.value) {
        is UiState.Error -> {
            Loading(isLoading = false)
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading(isLoading = true)
        is UiState.Success -> {
            Loading(isLoading = false)
            Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_LONG).show()
            onClick(false)
        }
    }
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        windowInsets = WindowInsets.ime,
        onDismissRequest = {
            onClick(false)
        },
        sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextFieldComponent(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TextFieldComponent(
                value = location,
                onValueChange = { location = it },
                label = "Location",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TextFieldComponent(
                value = price,
                onValueChange = { price = it },
                label = "Price",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                isLast = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onClick(false)
                        }
                    }
                }) {
                    Icon(Icons.Filled.Close, "closeIcon")
                }
                Button(onClick = {
                    viewModel.addShop(name, location, price, idItem ?: "")
                }) {
                    Text("Save")
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}