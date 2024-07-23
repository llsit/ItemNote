package com.example.itemnote

import androidx.lifecycle.ViewModel
import com.example.itemnote.data.model.ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class SharedViewModel : ViewModel() {
    private val _selectedItem = MutableStateFlow<ItemModel?>(null)
    val selectedItem = _selectedItem.asStateFlow()

    fun updateSelectedItemModel(newItemModel: ItemModel) {
        _selectedItem.value = newItemModel
    }
}