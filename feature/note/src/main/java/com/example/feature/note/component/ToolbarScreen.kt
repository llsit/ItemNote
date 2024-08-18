package com.example.feature.note.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.design.theme.ItemNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarScreen(
    title: String,
    isBack: Boolean,
    onManuClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title, color = Color.Black,
                fontSize = 18.sp
            )
        },
        modifier = Modifier.background(Color.White),
        navigationIcon = {
            if (isBack) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            } else {
                IconButton(onClick = {
                    onManuClick()
                }) {
                    Icon(Icons.Filled.Menu, "backIcon")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ItemNoteTheme {
        ToolbarScreen("Home", false)
    }
}