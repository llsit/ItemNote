package com.example.feature.note.screen.addItem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.core.data.utils.UiState
import com.example.core.domain.usecase.AddCategoryUseCase
import com.example.core.domain.usecase.AddItemUseCase
import com.example.core.domain.usecase.EditItemUseCase
import com.example.core.domain.usecase.GetCategoryUseCase
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import com.example.feature.note.screen.main.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val editItemUseCase: EditItemUseCase,
    getCategoryUseCase: GetCategoryUseCase,
) : BaseViewModel(getCategoryUseCase) {

    private val _uiStateAddItem = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddItem: StateFlow<UiState<Unit>> = _uiStateAddItem.asStateFlow()

    private val _uiStateEditItem = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateEditItem: StateFlow<UiState<Unit>> = _uiStateEditItem.asStateFlow()

    private val _uiErrorState = MutableStateFlow<List<AddEditItemErrorState>>(emptyList())
    val uiErrorState: StateFlow<List<AddEditItemErrorState>> = _uiErrorState.asStateFlow()

    private val _uiStateAddCategory = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddCategory: StateFlow<UiState<Unit>> = _uiStateAddCategory.asStateFlow()

    private val _screenMode = MutableStateFlow<AddEditItemMode>(AddEditItemMode.Add)
    val screenMode: StateFlow<AddEditItemMode> = _screenMode.asStateFlow()

    var name by mutableStateOf("")
        private set

    var imageUri by mutableStateOf("")
        private set

    var category by mutableStateOf<CategoryModel?>(null)
        private set

    private var itemModel: ItemModel? = null

    init {
        getCategory()
    }

    fun setScreenMode(mode: AddEditItemMode, itemModel: ItemModel?) {
        _screenMode.value = mode
        this.itemModel = itemModel
        if (mode is AddEditItemMode.Edit) {
            this.itemModel?.let {
                onNameChange(it.name)
                onCategoryChange(it.categoryModel)
                onImageUriChange(it.imageUrl)
            }
        }
    }

    fun onNameChange(newName: String) {
        name = newName
        _uiErrorState.update { currentErrors ->
            if (newName.isEmpty()) {
                if (!currentErrors.contains(AddEditItemErrorState.EmptyName)) {
                    currentErrors + AddEditItemErrorState.EmptyName
                } else {
                    currentErrors
                }
            } else {
                currentErrors.filter { it != AddEditItemErrorState.EmptyName }
            }
        }
    }

    fun onImageUriChange(newImageUri: String) {
        imageUri = newImageUri
    }

    fun onCategoryChange(newCategory: CategoryModel?) {
        category = newCategory
    }

    fun resetUiStateErrorCategory() {
        _uiErrorState.update { currentErrors ->
            currentErrors.filter { it != AddEditItemErrorState.EmptyCategory }
        }
    }

    fun checkAddEditItem() {
        checkErrorState()
        if (name.isNotEmpty() && category != null) {
            if (screenMode.value is AddEditItemMode.Add) {
                addItem()
            } else {
                editItem()
            }
        }
    }

    private fun editItem() = viewModelScope.launch {
        editItemUseCase.editItem(name, imageUri, category!!, itemModel!!).collect {
            when (it) {
                is UiState.Error -> {
                    _uiStateEditItem.value = UiState.Error(it.message)
                }

                UiState.Loading -> {
                    _uiStateEditItem.value = UiState.Loading
                }

                is UiState.Success -> {
                    _uiStateEditItem.value = UiState.Success(it.data)
                }

                else -> Unit
            }
        }
    }

    private fun addItem() = viewModelScope.launch {
        addItemUseCase.addItem(name, imageUri, category!!).collect {
            when (it) {
                is UiState.Error -> {
                    _uiStateAddItem.value = UiState.Error(it.message)
                }

                UiState.Loading -> {
                    _uiStateAddItem.value = UiState.Loading
                }

                is UiState.Success -> {
                    _uiStateAddItem.value = UiState.Success(it.data)
                }

                else -> Unit
            }
        }
    }

    fun addCategory(newCategory: String) = viewModelScope.launch {
        addCategoryUseCase.addCategory(newCategory).collect {
            when (it) {
                is UiState.Error -> {
                    _uiStateAddCategory.value = UiState.Error(it.message)
                }

                UiState.Idle -> Unit
                UiState.Loading -> _uiStateAddCategory.value = UiState.Loading
                is UiState.Success -> {
                    _uiStateAddCategory.value = UiState.Success(Unit)
                }
            }
        }
    }

    private fun checkErrorState() {
        val errorList = mutableListOf<AddEditItemErrorState>()

        if (name.isEmpty()) {
            errorList.add(AddEditItemErrorState.EmptyName)
        }

        if (category == null) {
            errorList.add(AddEditItemErrorState.EmptyCategory)
        }

        _uiErrorState.value = errorList
    }

}