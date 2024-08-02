package com.example.itemnote.screen.addItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.itemnote.screen.main.BaseViewModel
import com.example.itemnote.usecase.AddCategoryUseCase
import com.example.itemnote.usecase.AddItemUseCase
import com.example.itemnote.usecase.CategoryModel
import com.example.itemnote.usecase.GetCategoryUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    getCategoryUseCase: GetCategoryUseCase,
) : BaseViewModel(getCategoryUseCase) {

    private val _uiStateAddShop = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddShop: StateFlow<UiState<Unit>> = _uiStateAddShop.asStateFlow()

    private val _uiErrorState = MutableStateFlow<List<AddItemErrorState>>(emptyList())
    val uiErrorState: StateFlow<List<AddItemErrorState>> = _uiErrorState.asStateFlow()

    private val _uiStateAddCategory = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddCategory: StateFlow<UiState<Unit>> = _uiStateAddCategory.asStateFlow()

    var name by mutableStateOf("")
        private set

    var imageUri by mutableStateOf("")
        private set

    private var category by mutableStateOf<CategoryModel?>(null)

    init {
        getCategory()
    }

    fun onNameChange(newName: String) {
        name = newName
        _uiErrorState.update { currentErrors ->
            if (newName.isEmpty()) {
                if (!currentErrors.contains(AddItemErrorState.EmptyName)) {
                    currentErrors + AddItemErrorState.EmptyName
                } else {
                    currentErrors
                }
            } else {
                currentErrors.filter { it != AddItemErrorState.EmptyName }
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
            currentErrors.filter { it != AddItemErrorState.EmptyCategory }
        }
    }

    fun checkAddItem() {
        checkErrorState()
        if (name.isNotEmpty() && category != null) {
            addItem()
        }
    }

    private fun addItem() = viewModelScope.launch {
        addItemUseCase.addItem(name, imageUri, category!!).collect {
            when (it) {
                is UiState.Error -> {
                    _uiStateAddShop.value = UiState.Error(it.message)
                }

                UiState.Loading -> {
                    _uiStateAddShop.value = UiState.Loading
                }

                is UiState.Success -> {
                    _uiStateAddShop.value = UiState.Success(it.data)
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
        val errorList = mutableListOf<AddItemErrorState>()

        if (name.isEmpty()) {
            errorList.add(AddItemErrorState.EmptyName)
        }

        if (category == null) {
            errorList.add(AddItemErrorState.EmptyCategory)
        }

        _uiErrorState.value = errorList
    }

}