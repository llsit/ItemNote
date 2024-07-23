package com.example.itemnote

import androidx.lifecycle.ViewModel
import com.example.itemnote.data.model.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _selectedItem = MutableStateFlow<ItemModel?>(null)
    val selectedItem: StateFlow<ItemModel?> = _selectedItem.asStateFlow()

    fun updateSelectedItemModel(newItemModel: ItemModel) {
        _selectedItem.value = newItemModel
    }
}