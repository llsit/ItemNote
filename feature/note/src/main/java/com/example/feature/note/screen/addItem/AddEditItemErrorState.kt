package com.example.feature.note.screen.addItem

sealed class AddEditItemErrorState {
    data object EmptyName : AddEditItemErrorState()
    data object EmptyCategory : AddEditItemErrorState()
}