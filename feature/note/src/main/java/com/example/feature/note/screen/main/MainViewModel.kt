package com.example.feature.note.screen.main

import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.AuthState
import com.example.core.common.utils.Constants.Category.HOME
import com.example.core.common.utils.UiState
import com.example.core.data.repository.AuthRepository
import com.example.core.data.utils.DataStoreManager
import com.example.core.domain.usecase.note.GetCategoryUseCase
import com.example.core.domain.usecase.note.GetItemUseCase
import com.example.core.domain.usecase.note.GetItemsByCategory
import com.example.core.model.data.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val authRepository: AuthRepository,
    getCategoryUseCase: GetCategoryUseCase,
    private val getItemsByCategory: GetItemsByCategory,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel(getCategoryUseCase) {

    private val _uiState = MutableStateFlow<UiState<List<ItemModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        getCategory()
    }

    private fun getItems() = viewModelScope.launch {
        getItemUseCase.getItems()
            .onStart { _uiState.value = UiState.Loading }
            .catch { _uiState.value = UiState.Error(it.message.toString()) }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun getItemsByCategory(categoryId: String) = viewModelScope.launch {
        if (categoryId == HOME) {
            getItems()
        } else {
            getItemsByCategory.getItemsByCategory(categoryId)
                .onStart { _uiState.value = UiState.Loading }
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect {
                when (it) {
                    is UiState.Success -> {
                        _authState.value = AuthState.Unauthenticated
                    }

                    else -> Unit
                }
            }
        }
    }

    fun saveItemModel(itemModel: ItemModel) = viewModelScope.launch {
        dataStoreManager.saveItemModel(itemModel)
    }
}