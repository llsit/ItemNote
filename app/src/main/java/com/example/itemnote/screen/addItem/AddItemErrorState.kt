package com.example.itemnote.screen.addItem

sealed class AddItemErrorState {
    data object EmptyName : AddItemErrorState()
    data object EmptyCategory : AddItemErrorState()
}