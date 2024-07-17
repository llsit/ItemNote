package com.example.itemnote.screen.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.itemnote.utils.NavigationItem
import com.example.itemnote.utils.UiState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val state = viewModel.registerState.collectAsState()
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
                "Register Success",
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(NavigationItem.Login.route)
        }
    }
    Scaffold(
        topBar = {
            ToolbarScreen(title = "Register", false) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
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
                value = viewModel.name.value,
                onValueChange = viewModel::onNameChange,
                label = "Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TextFieldComponent(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChange,
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TextFieldComponent(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                label = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                isLast = true
            )
            Button(
                onClick = {
                    viewModel.registerUser(
                        viewModel.email.value,
                        viewModel.password.value,
                        viewModel.name.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Register")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
                TextButton(onClick = {
                    navController.navigate(NavigationItem.Login.route)
                }) {
                    Text(text = "Login Now", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen()
}
