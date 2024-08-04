package com.example.itemnote.screen.addItem

sealed class AddEditItemErrorState {
    data object EmptyName : AddEditItemErrorState()
    data object EmptyCategory : AddEditItemErrorState()
}