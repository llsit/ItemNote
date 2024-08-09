package com.example.itemnote.screen.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.itemnote.R
import com.example.itemnote.SharedViewModel
import com.example.itemnote.component.AddShopDialog
import com.example.itemnote.component.ConfirmDialog
import com.example.itemnote.component.FloatingButton
import com.example.itemnote.component.Loading
import com.example.itemnote.component.MediumToolbarComponent
import com.example.itemnote.component.ShopCard
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.screen.addItem.EditResult
import com.example.itemnote.utils.NavigationItem
import com.example.itemnote.utils.UiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ShopListScreen(
    navController: NavHostController,
    shopListViewModel: ShopListViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }
    var shopModel by remember { mutableStateOf<ShopModel?>(null) }
    val scope = rememberCoroutineScope()
    val state by shopListViewModel.uiStateGetShop.collectAsState()
    val selectedItemModel by shopListViewModel.selectedItem.collectAsState()
    val deleteItemState by shopListViewModel.deleteItemState.collectAsState()
    val result = navController.currentBackStackEntry?.savedStateHandle?.get<EditResult>("result")

    LaunchedEffect(key1 = result) {
        result?.let {
            if (it == EditResult.SUCCESS) {
                shopListViewModel.getItemById()
                navController.currentBackStackEntry?.savedStateHandle?.remove<EditResult>("result")
            }
        }
    }

    when (deleteItemState) {
        is UiState.Error -> {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
        }

        is UiState.Success -> {
            navController.popBackStack()
        }

        else -> Unit
    }

    Scaffold(
        topBar = {
            MediumToolbarComponent(
                title = "${selectedItemModel?.name}",
                isBack = true,
                onDeleteClick = {
                    showDialog = true
                },
                onEditClick = {
                    selectedItemModel?.let {
                        sharedViewModel.updateSelectedItemModel(it)
                    }
                    navController.navigate(NavigationItem.EditItem.route)
                },
                onBackClick = {
                    navController.popBackStack()
                    sharedViewModel.clearSelectedItem()
                }
            )
        },
        floatingActionButton = {
            FloatingButton {
                shopListViewModel.clearState()
                showBottomSheet = true
            }
        }
    )
    { innerPadding ->
        when (state) {
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
            }

            UiState.Idle -> Unit
            UiState.Loading -> Loading()
            is UiState.Success -> {
                ShopList(
                    innerPadding = innerPadding,
                    selectedItemModel = selectedItemModel,
                    shopList = (state as UiState.Success<List<ShopModel>>).data,
                    onDeleteShop = {
                        shopListViewModel.deleteShop(
                            shopId = it,
                            itemId = selectedItemModel?.id.orEmpty(),
                        )
                    },
                    onEditShop = {
                        shopModel = it
                        showUpdateDialog.value = true
                    }
                )
            }
        }

        if (showBottomSheet) {
            AddShopDialog(
                scope = scope,
                onClick = {
                    showBottomSheet = it
                    shopListViewModel.getShop()
                },
                onDismiss = {
                    showBottomSheet = false
                }
            )
        }

        if (showDialog) {
            ConfirmDialog(
                icon = Icons.Default.Info,
                dialogTitle = stringResource(id = R.string.dialog_confirm_title),
                dialogText = stringResource(id = R.string.dialog_confirm_delete),
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    if (selectedItemModel?.id?.isNotEmpty() == true)
                        shopListViewModel.deleteItem(selectedItemModel?.id.orEmpty())
                    showDialog = false
                })
        }

        if (showUpdateDialog.value) {
            AddShopDialog(
                model = shopModel,
                scope = scope,
                onUpdate = {
                    showUpdateDialog.value = it
                    shopListViewModel.getShop()
                },
                onDismiss = { showUpdateDialog.value = false }
            )
        }
    }
}

@Composable
fun ShopList(
    innerPadding: PaddingValues,
    selectedItemModel: ItemModel?,
    shopList: List<ShopModel>?,
    onDeleteShop: (String) -> Unit,
    onEditShop: (ShopModel) -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) }
    val deleteShopId = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .align(CenterHorizontally)
        if (selectedItemModel?.imageUrl.orEmpty().isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_product),
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                modifier = modifier
            )
        } else {
            AsyncImage(
                model = selectedItemModel?.imageUrl,
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.placeholder_product),
                error = painterResource(id = R.drawable.placeholder_product),
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Category Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Category: ${selectedItemModel?.categoryModel?.name ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            val dateFormat = SimpleDateFormat("EEE MMM dd", Locale.US)
            val formattedDate = dateFormat.format(Date())
            Text(
                text = "Date: $formattedDate",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        shopList?.forEachIndexed { index, shopModel ->
            ShopCard(
                model = shopModel,
                index = index,
                onEditClick = {
                    onEditShop(it)
                }, onDeleteClick = {
                    deleteShopId.value = shopModel.id
                    showDialog.value = true
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showDialog.value) {
        ConfirmDialog(
            icon = Icons.Default.Info,
            dialogTitle = stringResource(id = R.string.dialog_confirm_title),
            dialogText = stringResource(id = R.string.dialog_confirm_delete),
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                if (deleteShopId.value.isNotEmpty())
                    onDeleteShop(deleteShopId.value)
                showDialog.value = false
            })
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewShopList() {
    ShopList(
        innerPadding = PaddingValues(0.dp),
        selectedItemModel = ItemModel(
            name = "name",
            date = "date",
            imageUrl = "",
            shop = ShopModel(name = "name", location = "location", price = 10000.0)
        ),
        shopList = listOf(
            ShopModel(name = "name", location = "location", price = 10000.0),
            ShopModel(name = "name", location = "location", price = 10000.0)
        ),
        onDeleteShop = {}
    )
}

