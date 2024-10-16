package com.example.feature.note.screen.addItem

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import com.example.core.common.image.ImageUriUtils.getTempUri
import com.example.core.common.navigation.resultHandler
import com.example.core.common.utils.UiState
import com.example.core.design.R
import com.example.design.ui.Loading
import com.example.design.ui.NoInternetDialog
import com.example.design.ui.TextFieldComponent
import com.example.design.ui.ToolbarScreen
import com.example.feature.note.component.AddCategoryDialog
import com.example.feature.note.component.BasicAlertDialogComponent
import com.example.feature.note.component.ChipGroupList
import com.example.feature.note.component.MyModalBottomSheet
import com.example.feature.note.component.MySnackBarComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AddEditItemScreen(
    mode: AddEditItemMode,
    navController: NavHostController = rememberNavController(),
    viewModel: AddEditItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) {

    val state by viewModel.uiStateAddItem.collectAsState()
    val errorState by viewModel.uiErrorState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiStateAddCategory by viewModel.uiStateAddCategory.collectAsState(initial = UiState.Idle)
    val editItemState by viewModel.uiStateEditItem.collectAsState()
    val uiNoInternet by viewModel.uiNoInternet.collectAsState()
    val selectedItem by viewModel.selectedItem.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedItem) {
        viewModel.setScreenMode(mode)
    }

    if (uiNoInternet) {
        NoInternetDialog(showDialog) {
            showDialog = it
            viewModel.updateNoInternet()
        }
    }

    when (uiStateAddCategory) {
        is UiState.Error -> {
            Toast.makeText(
                context,
                "Error : Add Category Unsuccessful",
                Toast.LENGTH_LONG
            ).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading()
        is UiState.Success -> {
            LaunchedEffect(uiStateAddCategory) {
                viewModel.getCategory()
                Toast.makeText(context, "Add Category Success!", Toast.LENGTH_LONG).show()
            }
        }
    }
    when (state) {
        is UiState.Error -> {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }

        UiState.Loading -> {
            Loading()
        }

        is UiState.Success -> {
            LaunchedEffect(key1 = Unit) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Add Item Success!")
                }
            }
        }

        else -> Unit
    }
    when (editItemState) {
        is UiState.Error -> {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }

        UiState.Loading -> {
            Loading()
        }

        is UiState.Success -> {
            LaunchedEffect(key1 = Unit) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Edit Item Success!")
                }
            }
        }

        else -> Unit
    }

    Scaffold(
        topBar = {
            ToolbarScreen(
                title = if (mode is AddEditItemMode.Add) "Add Item" else "Edit Item",
                true
            ) {
                navController.popBackStack()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                MySnackBarComponent(data.visuals.message)
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        delay(1000)
                        snackbarHostState.currentSnackbarData?.dismiss()
                        navController.resultHandler<EditResult>("result").setResult(
                            EditResult.SUCCESS
                        )
                        navController.popBackStack()
                    }
                }
            }
        }
    )
    { innerPadding ->
        AddItemComponent(
            context = context,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            isTextFieldError = errorState.contains(AddEditItemErrorState.EmptyName),
            showErrorCategory = errorState.contains(AddEditItemErrorState.EmptyCategory),
            onDismiss = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun AddItemComponent(
    context: Context,
    modifier: Modifier,
    viewModel: AddEditItemViewModel,
    isTextFieldError: Boolean,
    showErrorCategory: Boolean,
    onDismiss: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }
    val category by viewModel.uiStateCategory.collectAsState()

    Column(
        modifier.verticalScroll(rememberScrollState()),
    ) {
        ImageSection(
            onSelectImage = { viewModel.onImageUriChange(it.toString()) },
            imageUri = viewModel.imageUri,
            onClickClearImage = { viewModel.onImageUriChange("") },
            context = context
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldComponent(
                value = viewModel.name,
                onValueChange = { name -> viewModel.onNameChange(name) },
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
            when (category) {
                UiState.Loading -> Loading()
                is UiState.Success -> {
                    ChipGroupList(
                        categoryList = (category as UiState.Success).data,
                        onSelected = { viewModel.onCategoryChange(it) },
                        selectedId = viewModel.category?.id.orEmpty()
                    )
                }

                else -> Unit
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    onDismiss()
                }) {
                    Text("Back")
                }
                Button(onClick = {
                    focusManager.clearFocus()
                    viewModel.checkAddEditItem()
                }) {
                    Text("Save")
                }
            }
        }

        if (showErrorCategory) {
            BasicAlertDialogComponent(
                title = "Please select a Category",
                onClickDismiss = { viewModel.resetUiStateErrorCategory() },
            )
        }

        AddCategoryDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onAddCategory = { newCategory ->
                viewModel.addCategory(newCategory)
            }
        )
    }
}

@Composable
fun ImageSection(
    onSelectImage: (Uri) -> Unit,
    imageUri: String,
    onClickClearImage: () -> Unit = {},
    context: Context
) {
    val uri = remember { mutableStateOf<Uri?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val authority = stringResource(id = R.string.fileprovider)
    val file = File(context.cacheDir, "images")

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                uri.value?.let {
                    onSelectImage.invoke(it)
                }
            }
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { image ->
            image?.let {
                onSelectImage.invoke(it)
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
            if (imageUri.isNotEmpty()) {
                Box {
                    val request = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(imageUri))
                        .crossfade(true)
                        .build()
                    AsyncImage(
                        model = request,
                        contentDescription = null,
                        modifier = imageModifier,
                    )

                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 12.dp, y = -12.dp)
                            .size(24.dp)
                    ) {
                        IconButton(
                            onClick = { onClickClearImage() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Red
                            )
                        }
                    }
                }
            } else {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Image",
                    modifier = imageModifier,
                )
            }
        }
    }

    if (showBottomSheet) {
        MyModalBottomSheet(
            header = "Choose Option",
            onDismiss = { showBottomSheet = false },
            onTakePhotoClick = {
                showBottomSheet = false
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
                showBottomSheet = false
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
    }
}

