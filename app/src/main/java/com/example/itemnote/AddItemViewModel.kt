package com.example.itemnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.usecase.AddItemUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    private val _uiStateAddShop = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiStateAddShop: StateFlow<UiState<Unit>> = _uiStateAddShop

    private val _uiStateEmptyName = MutableStateFlow<Boolean>(false)
    val uiStateEmptyName: StateFlow<Boolean> = _uiStateEmptyName

    fun addItem(name: String) = viewModelScope.launch {

        addItemUseCase.addItem(name).collect {
            if (name.isEmpty()) {
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
                }
            } else {
                _uiStateEmptyName.value = true
            }
        }
    }

}