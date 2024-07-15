package com.example.itemnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.usecase.AddShopUseCase
import com.example.itemnote.usecase.GetShopUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val addShopUseCase: AddShopUseCase,
    private val getShopUseCase: GetShopUseCase
) : ViewModel() {

    private val _uiStateAddShop = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddShop: StateFlow<UiState<Unit>> = _uiStateAddShop.asStateFlow()

    private val _uiStateGetShop = MutableStateFlow<UiState<List<ShopModel>>>(UiState.Idle)
    val uiStateGetShop: StateFlow<UiState<List<ShopModel>>> = _uiStateGetShop.asStateFlow()

    fun addShop(name: String, location: String, price: String, idItem: String) =
        viewModelScope.launch {
            addShopUseCase.addShop(name, location, price, idItem).collect {
                when (it) {
                    is UiState.Error -> {
                        _uiStateAddShop.value = UiState.Error(it.message)
                    }

                    UiState.Idle -> Unit
                    UiState.Loading -> _uiStateAddShop.value = UiState.Loading
                    is UiState.Success -> {
                        _uiStateAddShop.value = UiState.Success(it.data)
                    }
                }
            }
        }

    fun getShop(idItem: String) = viewModelScope.launch {
        getShopUseCase.getShop(idItem).collect {
            when (it) {
                is UiState.Error -> {
                    _uiStateGetShop.value = UiState.Error(it.message)
                }

                UiState.Idle -> Unit
                UiState.Loading -> _uiStateGetShop.value = UiState.Loading
                is UiState.Success -> {
                    _uiStateGetShop.value = UiState.Success(it.data)
                }
            }
        }
    }
}