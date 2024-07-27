package com.example.itemnote.screen.addItem

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.itemnote.R
import com.example.itemnote.component.AddCategoryDialog
import com.example.itemnote.component.BasicAlertDialogComponent
import com.example.itemnote.component.ChipGroupList
import com.example.itemnote.component.Loading
import com.example.itemnote.component.MyModalBottomSheet
import com.example.itemnote.component.MySnackBarComponent
import com.example.itemnote.component.TextFieldComponent
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.utils.ImageUriUtils.getTempUri
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AddItemScreen(
    navController: NavHostController = rememberNavController(),
    addViewModel: AddItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) {

    val state = addViewModel.uiStateAddShop.collectAsState()
    val errorState by addViewModel.uiStateEmptyName.collectAsState()
    val uri = remember { mutableStateOf<Uri?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiStateAddCategory = addViewModel.uiStateAddCategory.collectAsState()

    val authority = stringResource(id = R.string.fileprovider)
    val file = File(context.cacheDir, "images")

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            uri.value?.let {
                addViewModel.onImageUriChange(it.toString())
            }
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->
            imageUri?.let {
                uri.value = it
                addViewModel.onImageUriChange(it.toString())
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, proceed to step 2
            val tempUri = getTempUri(context, authority, file)
            uri.value = tempUri
            uri.value?.let {
                takePhotoLauncher.launch(it)
            }
        } else {
            // Permission is denied, handle it accordingly
        }
    }

    when (uiStateAddCategory.value) {
        is UiState.Error -> {
            Loading(isLoading = false)
            Toast.makeText(
                LocalContext.current,
                "Error : Add Category Unsuccessful",
                Toast.LENGTH_LONG
            ).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading(isLoading = true)
        is UiState.Success -> {
            Loading(isLoading = true)
            addViewModel.getCategory()
            Toast.makeText(
                LocalContext.current,
                "Add Category Success!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            ToolbarScreen(title = "Add Item", true) {
                navController.popBackStack()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                MySnackBarComponent(data.visuals.message)
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        delay(1000)
                        navController.popBackStack()
                    }
                }
            }
        }
    )
    { innerPadding ->
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
                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Add Item Success!")
                    }
                }
            }

            else -> {
                Loading(isLoading = false)
            }
        }
        AddItemComponent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            addViewModel = addViewModel,
            isTextFieldError = errorState,
            onTakePhotoClick = {
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is already granted, proceed to step 2
                    val tempUri = getTempUri(context, authority, file)
                    uri.value = tempUri
                    uri.value?.let {
                        takePhotoLauncher.launch(it)
                    }
                } else {
                    // Permission is not granted, request it
                    cameraPermissionLauncher.launch(permission)
                }
            },
            onPhotoGalleryClick = {
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
    }
}

@Composable
fun AddItemComponent(
    modifier: Modifier,
    navController: NavHostController,
    addViewModel: AddItemViewModel,
    isTextFieldError: Boolean,
    onTakePhotoClick: () -> Unit,
    onPhotoGalleryClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val showCategoryDialog by addViewModel.uiStateErrorCategory.collectAsState()
    val category = addViewModel.uiStateCategory.collectAsState()

    Column(
        modifier.verticalScroll(rememberScrollState()),
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
                modifier = Modifier
                    .clickable(onClick = { showBottomSheet = true })
                    .align(Alignment.CenterHorizontally),
            ) {
                val imageModifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .wrapContentHeight()
                if (addViewModel.imageUri.isNotEmpty()) {
                    val request = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(addViewModel.imageUri))
                        .crossfade(true)
                        .build()
                    AsyncImage(
                        model = request,
                        contentDescription = null,
                        modifier = imageModifier,
                    )
                } else {
                    Image(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Image",
                        modifier = imageModifier,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldComponent(
                value = addViewModel.name,
                onValueChange = { name -> addViewModel.onNameChange(name) },
                label = "Name",
                isError = isTextFieldError,
                errorMessage = "Name is Empty",
                isLast = true,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "Category",
                )
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add category",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            when (category.value) {
                is UiState.Error -> {
                    Loading(isLoading = false)
                    Toast.makeText(
                        LocalContext.current,
                        "Error : Get Category Unsuccessful",
                        Toast.LENGTH_LONG
                    ).show()
                }

                UiState.Idle -> Unit
                UiState.Loading -> Loading(isLoading = true)
                is UiState.Success -> {
                    Loading(isLoading = false)
                    (category.value as UiState.Success).data.let {
                        ChipGroupList(
                            categoryList = it,
                            onSelected = { addViewModel.onCategoryChange(it) }
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
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
                    focusManager.clearFocus()
                    addViewModel.checkAddItem()
                }) {
                    Text("Save")
                }
            }
        }

        if (showBottomSheet) {
            MyModalBottomSheet(
                header = "Choose Option",
                onDismiss = { showBottomSheet = false },
                onTakePhotoClick = {
                    showBottomSheet = false
                    onTakePhotoClick()
                },
                onPhotoGalleryClick = {
                    showBottomSheet = false
                    onPhotoGalleryClick()
                }
            )
        }
        if (showCategoryDialog) {
            BasicAlertDialogComponent(
                title = "Please select a Category",
                onClickDismiss = { addViewModel.resetUiStateErrorCategory() },
            )
        }

        AddCategoryDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onAddCategory = { newCategory ->
                addViewModel.addCategory(newCategory)
            }
        )
    }
}

