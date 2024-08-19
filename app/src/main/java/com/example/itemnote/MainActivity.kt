package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.common.navigation.NavigationItem
import com.example.core.data.utils.SharedViewModel
import com.example.design.theme.ItemNoteTheme
import com.example.feature.authentication.navigation.authenNavigation
import com.example.feature.authentication.screen.LoginScreen
import com.example.feature.authentication.screen.RegisterScreen
import com.example.feature.note.navigation.noteNavigation
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
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Login.route
        ) {
            authenNavigation(
                navController = navController
            )
            noteNavigation(
                navController = navController,
                sharedViewModel = sharedViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
