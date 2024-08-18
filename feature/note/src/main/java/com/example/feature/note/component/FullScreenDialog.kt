package com.example.feature.note.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.data.utils.UiState
import com.example.feature.note.screen.shop.ShopListViewModel

@Composable
fun FullScreenDialog(
    viewModel: ShopListViewModel = hiltViewModel(),
    onClick: (Boolean) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val state by viewModel.uiStateShop.collectAsState()
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
    Dialog(
        onDismissRequest = {
            onClick(false)
        },
        DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .zIndex(10F),
            topBar = {
                ToolbarScreen(title = "Shop List", true) {
                    onClick(false)
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") }
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        onClick(false)
                    }) {
                        Icon(Icons.Filled.Close, "closeIcon")
                    }
                    Button(onClick = {
                        viewModel.addShop()
                    }) {
                        Text("Save")
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewFullScreenDialog() {
    FullScreenDialog()
}