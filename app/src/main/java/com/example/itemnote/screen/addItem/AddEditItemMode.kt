package com.example.itemnote.screen.addItem

sealed class AddEditItemMode {
    object Add : AddEditItemMode()
    object Edit : AddEditItemMode()
}
