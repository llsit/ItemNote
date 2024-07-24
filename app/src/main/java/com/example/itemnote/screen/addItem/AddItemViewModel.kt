package com.example.itemnote.screen.addItem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.usecase.AddCategoryUseCase
import com.example.itemnote.usecase.AddItemUseCase
import com.example.itemnote.usecase.CategoryModel
import com.example.itemnote.usecase.GetCategoryUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiStateAddShop = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddShop: StateFlow<UiState<Unit>> = _uiStateAddShop.asStateFlow()

    private val _uiStateEmptyName = MutableStateFlow(false)
    val uiStateEmptyName: StateFlow<Boolean> = _uiStateEmptyName

    private val _uiStateAddCategory = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddCategory: StateFlow<UiState<Unit>> = _uiStateAddCategory.asStateFlow()

    private val _uiStateCategory = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Idle)
    val uiStateCategory: StateFlow<UiState<List<CategoryModel>>> = _uiStateCategory.asStateFlow()

    var name by mutableStateOf("")
        private set

    var imageUri by mutableStateOf("")
        private set

    init {
        getCategory()
    }

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onImageUriChange(newImageUri: String) {
        imageUri = newImageUri
    }

    fun addItem() = viewModelScope.launch {
        addItemUseCase.addItem(name, imageUri).collect {
            if (name.isNotEmpty()) {
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
            } else {
                _uiStateEmptyName.value = true
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

    fun getCategory() = viewModelScope.launch {
        getCategoryUseCase.getCategory()
            .onStart { _uiStateCategory.value = UiState.Loading }
            .collect {
                when (it) {
                    is UiState.Error -> _uiStateCategory.value = UiState.Error(it.message)
                    UiState.Idle -> Unit
                    UiState.Loading -> _uiStateCategory.value = UiState.Loading
                    is UiState.Success -> {
                        _uiStateCategory.value = UiState.Success(it.data)
                    }
                }
            }
    }

}