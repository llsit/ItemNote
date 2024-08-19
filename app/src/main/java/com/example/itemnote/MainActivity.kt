package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.core.data.utils.SharedViewModel
import com.example.design.theme.ItemNoteTheme
import com.example.itemnote.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    ItemNoteTheme {
        val navController = rememberNavController()
        MainNavHost(
            navController = navController,
            sharedViewModel = sharedViewModel
        )
    }
}
