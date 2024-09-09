package com.example.feature.note.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.common.utils.UiState
import com.example.core.model.data.ShopModel
import com.example.design.ui.Loading
import com.example.design.ui.TextFieldComponent
import com.example.feature.note.screen.shop.ShopListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShopDialog(
    model: ShopModel? = null,
    viewModel: ShopListViewModel = hiltViewModel(),
    onClick: (Boolean) -> Unit = {},
    onUpdate: (Boolean) -> Unit = {},
    onDismiss: (Boolean) -> Unit = {}
) {

    val state by viewModel.uiStateShop.collectAsState()
    val errorName by viewModel.nameState.collectAsState()
    val errorLocation by viewModel.locationState.collectAsState()
    val errorPrice by viewModel.priceState.collectAsState()

    when (state) {
        is UiState.Error -> {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading()
        is UiState.Success -> {
            Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_LONG).show()
            onClick(false)
        }
    }

    LaunchedEffect(Unit) {
        model?.let { viewModel.setUpdate(it) }
    }

    BasicAlertDialog(
        onDismissRequest = {}
    ) {
        AddShopDialogSection(
            model = model,
            shopName = viewModel.name,
            shopLocation = viewModel.location,
            price = viewModel.price,
            onUpdateName = { viewModel.updateName(it) },
            onUpdateLocation = { viewModel.updateLocation(it) },
            onUpdatePrice = { viewModel.updatePrice(it) },
            errorName = errorName,
            errorLocation = errorLocation,
            errorPrice = errorPrice,
            onClick = {
                onClick(it)
                viewModel.addShop()
            },
            onUpdate = {
                onUpdate(it)
                viewModel.updateShop(model)
            },
            onDismiss = {
                onDismiss(it)
                viewModel.clearState()
            },
        )
    }
}

@Composable
fun AddShopDialogSection(
    model: ShopModel?,
    scope: CoroutineScope = rememberCoroutineScope(),
    shopName: String,
    shopLocation: String,
    price: String,
    errorName: String,
    errorLocation: String,
    errorPrice: String,
    onUpdateName: (String) -> Unit = {},
    onUpdateLocation: (String) -> Unit = {},
    onUpdatePrice: (String) -> Unit = {},
    onClick: (Boolean) -> Unit = {},
    onUpdate: (Boolean) -> Unit = {},
    onDismiss: (Boolean) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Enter your shop details",
                style = MaterialTheme.typography.headlineSmall
            )
            TextFieldComponent(
                value = shopName,
                onValueChange = { onUpdateName(it) },
                label = "Shop Name",
                modifier = Modifier.fillMaxWidth(),
                isError = errorName.isNotEmpty(),
                errorMessage = errorName.ifEmpty { "Shop Name cannot be empty" }
            )
            TextFieldComponent(
                value = shopLocation,
                onValueChange = { onUpdateLocation(it) },
                label = "Shop Location",
                modifier = Modifier.fillMaxWidth(),
                isError = errorLocation.isNotEmpty(),
                errorMessage = errorLocation.ifEmpty { "Shop Location cannot be empty" }
            )
            TextFieldComponent(
                value = price,
                onValueChange = { price ->
                    price.toDoubleOrNull()?.let { onUpdatePrice(price) }
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
                    scope.launch { onDismiss(false) }
                }) {
                    Icon(Icons.Filled.Close, "closeIcon")
                }
                Button(onClick = {
                    scope.launch {
                        focusManager.clearFocus()
                        if (model == null) {
                            onClick(false)
                        } else {
                            onUpdate(false)
                        }
                    }
                }) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddShopDialogPreview() {
    AddShopDialogSection(
        model = null,
        scope = rememberCoroutineScope(),
        errorName = "",
        errorLocation = "",
        errorPrice = "",
        shopName = "",
        shopLocation = "",
        price = ""
    )
}