package com.example.itemnote.screen.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.usecase.AddShopUseCase
import com.example.itemnote.usecase.DeleteItemUseCase
import com.example.itemnote.usecase.DeleteShopUseCase
import com.example.itemnote.usecase.GetShopUseCase
import com.example.itemnote.usecase.UpdateShopUseCase
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
    private val deleteShopUseCase: DeleteShopUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateShopUseCase: UpdateShopUseCase
) : ViewModel() {

    private val itemId = savedStateHandle.get<String>("id") ?: ""
    private val _uiStateShop = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiStateShop: StateFlow<UiState<Unit>> = _uiStateShop.asStateFlow()

    private val _uiStateGetShop = MutableStateFlow<UiState<List<ShopModel>>>(UiState.Idle)
    val uiStateGetShop: StateFlow<UiState<List<ShopModel>>> = _uiStateGetShop.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _locationState = MutableStateFlow("")
    val locationState: StateFlow<String> = _locationState.asStateFlow()

    private val _priceState = MutableStateFlow("")
    val priceState: StateFlow<String> = _priceState.asStateFlow()

    private val _deleteItemState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteItemState: StateFlow<UiState<Unit>> = _deleteItemState.asStateFlow()

    var name by mutableStateOf("")
        private set
    var location by mutableStateOf("")
        private set
    var price by mutableStateOf("")
        private set


    init {
        getShop()
    }

    fun updateName(input: String) {
        name = input
    }

    fun updateLocation(input: String) {
        location = input
    }

    fun updatePrice(input: String) {
        price = input
    }

    fun addShop() {
        checkError()
        if (name.isNotEmpty() && location.isNotEmpty() && price.isNotEmpty() && price.toDouble() >= 0) {
            submitShopData()
        }
    }

    fun updateShop(shopModel: ShopModel?) {
        checkError()
        if (name.isNotEmpty() && location.isNotEmpty() && price.isNotEmpty() && price.toDouble() >= 0 && shopModel != null) {
            updateShopData(shopModel)
        }
    }

    private fun updateShopData(shopModel: ShopModel) = viewModelScope.launch {
        val newModel = shopModel.copy(
            name = name,
            location = location,
            price = price.ifEmpty { "0.0" }.toDouble()
        )
        updateShopUseCase.updateShop(itemId, newModel)
            .onStart { _uiStateShop.value = UiState.Loading }
            .collect {
                when (it) {
                    is UiState.Error -> {
                        _uiStateShop.value = UiState.Error(it.message)
                    }

                    UiState.Idle -> Unit
                    UiState.Loading -> _uiStateShop.value = UiState.Loading
                    is UiState.Success -> {
                        _uiStateShop.value = UiState.Success(it.data)
                    }
                }
            }

    }

    private fun submitShopData() {
        viewModelScope.launch {
            addShopUseCase.addShop(name, location, price, itemId)
                .onStart { _uiStateShop.value = UiState.Loading }
                .collect {
                    when (it) {
                        is UiState.Error -> {
                            _uiStateShop.value = UiState.Error(it.message)
                        }

                        UiState.Idle -> Unit
                        UiState.Loading -> _uiStateShop.value = UiState.Loading
                        is UiState.Success -> {
                            _uiStateShop.value = UiState.Success(it.data)
                        }
                    }
                }
        }
    }

    fun getShop() = viewModelScope.launch {
        getShopUseCase.getShop(itemId).collect {
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

    fun deleteShop(shopId: String, itemId: String) = viewModelScope.launch {
        deleteShopUseCase.deleteShop(itemId, shopId)
            .onStart { _uiStateGetShop.value = UiState.Loading }
            .collect {
                when (it) {
                    is UiState.Success -> getShop()
                    else -> Unit
                }
            }
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        deleteItemUseCase.deleteItem(itemId).collect {
            when (it) {
                is UiState.Success -> _deleteItemState.value = UiState.Success(it.data)
                else -> Unit
            }
        }
    }

    fun clearState() {
        _uiStateShop.value = UiState.Idle
        _nameState.value = ""
        _locationState.value = ""
        _priceState.value = ""
        name = ""
        location = ""
        price = ""
    }

    fun setUpdate(model: ShopModel) {
        name = model.name
        location = model.location
        price = model.price.toString()
    }

    private fun checkError() {
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
        if (price.isEmpty() || price.toDouble() < 0) {
            _priceState.value = "Price cannot be lower than 0"
        } else {
            _priceState.value = ""
        }
    }
}
