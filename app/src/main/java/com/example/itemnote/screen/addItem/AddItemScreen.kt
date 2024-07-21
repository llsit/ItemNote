package com.example.itemnote.screen.addItem

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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

    val state = addViewModel.uiStateAddShop.collectAsState()
    val errorState = addViewModel.uiStateEmptyName.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    val isTextFieldError = remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, proceed to step 2

        } else {
            // Permission is denied, handle it accordingly
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->

        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->

        }
    )

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
        AddItemComponent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            addViewModel = addViewModel,
            isTextFieldError = isTextFieldError,
        )
    }
}

@Composable
fun AddItemComponent(
    modifier: Modifier,
    navController: NavHostController,
    addViewModel: AddItemViewModel,
    isTextFieldError: MutableState<Boolean>,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                fontSize = 16.sp,
                text = "Image"
            )
            Box(
                modifier = modifier
                    .wrapContentSize()
                    .clickable(onClick = { showBottomSheet = true })
                    .align(Alignment.CenterHorizontally),
            ) {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .wrapContentHeight(),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextFieldComponent(
                value = addViewModel.name,
                onValueChange = addViewModel::onNameChange,
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
                    addViewModel.addItem()
                }) {
                    Text("Save")
                }
            }
        }

        if (showBottomSheet) {
            MyModalBottomSheet(
                header = "Choose Option",
                onDismiss = { showBottomSheet = false },
                onTakePhotoClick = { },
                onPhotoGalleryClick = { }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    header: String,
    onDismiss: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPhotoGalleryClick: () -> Unit
) {
    ModalBottomSheet(
        shape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = header,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            ItemListComponent(
                title = "Take Photo",
                icon = Icons.Default.AccountBox,
                onClick = onTakePhotoClick
            )
            ItemListComponent(
                title = "Photo Gallery",
                icon = Icons.Default.Place,
                onClick = onPhotoGalleryClick
            )
        }
    }
}

@Composable
fun ItemListComponent(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddItem() {
    AddItemScreen()
}
