package com.example.feature.note.screen.addItem

sealed class AddEditItemMode {
    object Add : AddEditItemMode()
    object Edit : AddEditItemMode()
}

enum class EditResult {
    SUCCESS
}
