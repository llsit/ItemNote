package com.example.feature.note.screen.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.data.utils.UiState
import com.example.feature.note.R
import com.example.feature.note.component.Loading
import com.example.feature.note.component.TextFieldComponent
import com.example.feature.note.component.ToolbarScreen
import com.example.feature.note.utils.NavigationItem

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) {
    val state = viewModel.registerState.collectAsState()
    when (state.value) {
        is UiState.Error -> {
            Toast.makeText(
                context,
                (state.value as UiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading()
        is UiState.Success -> {
            Toast.makeText(
                context,
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
        RegisterSection(
            navController = navController,
            innerPadding = innerPadding,
            name = viewModel.name.value,
            email = viewModel.email.value,
            password = viewModel.password.value,
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onClickRegister = {
                viewModel.registerUser()
            }
        )
    }
}

@Composable
fun RegisterSection(
    navController: NavHostController,
    innerPadding: PaddingValues,
    name: String,
    email: String,
    password: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickRegister: () -> Unit
) {
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
            value = name,
            onValueChange = { onNameChange(it) },
            label = "Name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
        TextFieldComponent(
            value = email,
            onValueChange = { onEmailChange(it) },
            label = "Email",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardType = KeyboardType.Email
        )
        TextFieldComponent(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = "Password",
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isLast = true,
            keyboardType = KeyboardType.Password
        )
        Button(
            onClick = { onClickRegister() },
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

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterSection(
        navController = rememberNavController(),
        innerPadding = PaddingValues(),
        name = "",
        email = "",
        password = "",
        onNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onClickRegister = {}
    )
}
