package com.example.itemnote.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itemnote.screen.shop.ShopListViewModel
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddShopBottomSheet(
    scope: CoroutineScope = rememberCoroutineScope(),
    viewModel: ShopListViewModel = hiltViewModel(),
    onClick: (Boolean) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val state = viewModel.uiStateAddShop.collectAsState()
    val errorName by viewModel.nameState.collectAsState()
    val errorLocation by viewModel.locationState.collectAsState()
    val errorPrice by viewModel.priceState.collectAsState()

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

    Dialog(
        onDismissRequest = {
            onClick(false)
            viewModel.clearState()
        }
    ) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextFieldComponent(
                    value = viewModel.name,
                    onValueChange = viewModel::updateName,
                    label = "Name",
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorName.isNotEmpty(),
                    errorMessage = errorName.ifEmpty { "Name cannot be empty" }
                )
                TextFieldComponent(
                    value = viewModel.location,
                    onValueChange = viewModel::updateLocation,
                    label = "Location",
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorLocation.isNotEmpty(),
                    errorMessage = errorLocation.ifEmpty { "Location cannot be empty" }
                )
                TextFieldComponent(
                    value = viewModel.price,
                    onValueChange = { price ->
                        price.toDoubleOrNull()?.let { viewModel.updatePrice(price) }
                    },
                    label = "Price",
                    modifier = Modifier.fillMaxWidth(),
                    isLast = true,
                    isError = errorPrice.isNotEmpty(),
                    errorMessage = errorPrice.ifEmpty { "Price cannot be lower than 0" },
                    keyboardType = KeyboardType.Decimal
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        scope.launch { onClick(false) }.invokeOnCompletion {
                            onClick(false)
                            viewModel.clearState()
                        }
                    }) {
                        Icon(Icons.Filled.Close, "closeIcon")
                    }
                    Button(onClick = {
                        focusManager.clearFocus()
                        viewModel.addShop()
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}