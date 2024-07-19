package com.example.itemnote.screen.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itemnote.R
import com.example.itemnote.component.Loading
import com.example.itemnote.component.TextFieldComponent
import com.example.itemnote.component.ToolbarScreen
import com.example.itemnote.utils.AuthState
import com.example.itemnote.utils.NavigationItem
import com.example.itemnote.utils.UiState

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    when (state.value) {
        is UiState.Error -> {
            Loading(isLoading = false)
            Toast.makeText(
                LocalContext.current,
                (state.value as UiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading(isLoading = true)
        is UiState.Success -> {
            Loading(isLoading = false)
            Toast.makeText(
                LocalContext.current,
                "Login Success",
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(NavigationItem.Main.route)
        }
    }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate(NavigationItem.Main.route)
            is AuthState.Unauthenticated -> Unit
            else -> {} // Do nothing for Initial and Loading states
        }
    }
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Login", false)
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.ime),
        ) {
            Image(
                painter = painterResource(R.drawable.welcome), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally)
            )
            TextFieldComponent(
                value = viewModel.username.value,
                onValueChange = viewModel::onUsernameChange,
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TextFieldComponent(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                label = "Password",
                isPassword = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                isLast = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                TextButton(onClick = { }) {
                    Text(text = "Reset")
                }
            }
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Login")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
                TextButton(onClick = {
                    navController.navigate(NavigationItem.Register.route)
                }) {
                    Text(text = "Register Now", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}