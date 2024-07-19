package com.example.itemnote.screen.addItem

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itemnote.component.AlertDialogDefault
import com.example.itemnote.component.Loading
import com.example.itemnote.component.TextFieldComponent
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddItemScreen(
    navController: NavHostController = rememberNavController(),
    addViewModel: AddItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    var name by remember { mutableStateOf("") }
    val state = addViewModel.uiStateAddShop.collectAsState()
    val errorState = addViewModel.uiStateEmptyName.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    val isTextFieldError = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Add Item", true) {
                navController.popBackStack()
            }
        },
    )
    { innerPadding ->
        when (errorState.value) {
            true -> {
                isTextFieldError.value = true
                Toast.makeText(LocalContext.current, "Name is Empty", Toast.LENGTH_LONG).show()
            }

            false -> {

            }
        }
        when (state.value) {
            is UiState.Error -> {
                Loading(isLoading = false)
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_LONG).show()
            }

            UiState.Loading -> {
                Loading(isLoading = true)
            }

            is UiState.Success -> {
                Loading(isLoading = false)
                Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_LONG).show()
                AlertDialogDefault(
                    onDismissRequest = {
                        openAlertDialog.value = false
                        coroutineScope.launch {
                            delay(1000)
                            navController.popBackStack()
                        }
                    },
                    onConfirmation = null,
                    dialogTitle = "Add Item Success",
                    icon = Icons.Default.Info
                )
            }

            else -> {
                Loading(isLoading = false)
            }
        }
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    text = "Image"
                )
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .clickable { }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextFieldComponent(
                    value = name,
                    onValueChange = { name = it },
                    label = "Name",
                    isError = isTextFieldError.value,
                    isLast = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("Back")
                    }
                    Button(onClick = {
                        addViewModel.addItem(name)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddItem() {
    AddItemScreen()
}
