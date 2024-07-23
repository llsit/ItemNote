package com.example.itemnote.screen.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addShopUseCase: AddShopUseCase,
    private val getShopUseCase: GetShopUseCase,
) : ViewModel() {

    private val idItem = savedStateHandle.get<String>("id") ?: ""
    private val _uiStateAddShop = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateAddShop: StateFlow<UiState<Unit>> = _uiStateAddShop.asStateFlow()

    private val _uiStateGetShop = MutableStateFlow<UiState<List<ShopModel>>>(UiState.Idle)
    val uiStateGetShop: StateFlow<UiState<List<ShopModel>>> = _uiStateGetShop.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _locationState = MutableStateFlow("")
    val locationState: StateFlow<String> = _locationState.asStateFlow()

    private val _priceState = MutableStateFlow("")
    val priceState: StateFlow<String> = _priceState.asStateFlow()

    var name by mutableStateOf("")
        private set
    var location by mutableStateOf("")
        private set
    var price by mutableIntStateOf(0)
        private set

    fun updateName(input: String) {
        name = input
    }

    fun updateLocation(input: String) {
        location = input
    }

    fun updatePrice(input: String) {
        price = input.ifEmpty { "0" }.toInt()
    }

    fun addShop() {
        if (name.isEmpty()) {
            _nameState.value = "Name cannot be empty"
        } else {
            _nameState.value = ""
        }
        if (location.isEmpty()) {
            _locationState.value = "Location cannot be empty"
        } else {
            _locationState.value = ""
        }
        if (price <= 0) {
            _priceState.value = "Price cannot be lower than 0"
        } else {
            _priceState.value = ""
        }
        if (name.isNotEmpty() && location.isNotEmpty() && price > 0) {
            submitShopData()
        }
    }

    private fun submitShopData() {
        viewModelScope.launch {
            addShopUseCase.addShop(name, location, price.toString(), idItem)
                .onStart { _uiStateAddShop.value = UiState.Loading }
                .collect {
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
    }

    fun getShop() = viewModelScope.launch {
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
