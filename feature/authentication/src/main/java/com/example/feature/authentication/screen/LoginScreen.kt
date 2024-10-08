package com.example.feature.authentication.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.common.navigation.NavigationItem
import com.example.core.common.utils.AuthState
import com.example.core.common.utils.UiState
import com.example.core.design.R
import com.example.design.ui.Loading
import com.example.design.ui.TextFieldComponent
import com.example.design.ui.ToolbarScreen

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val state = viewModel.uiState.collectAsState()
    val authState by viewModel.authState.collectAsState()
    when (state.value) {
        is UiState.Error -> {
            Toast.makeText(context, (state.value as UiState.Error).message, Toast.LENGTH_SHORT)
                .show()
        }

        UiState.Idle -> Unit
        UiState.Loading -> Loading()
        is UiState.Success -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.login_success),
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(NavigationItem.Main.route)
        }
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(NavigationItem.Main.route)
            }

            is AuthState.Unauthenticated -> Unit

            else -> Unit
        }
    }
    Scaffold(
        topBar = {
            ToolbarScreen(title = stringResource(id = R.string.login_title), false)
        }
    ) { innerPadding ->
        LoginSection(
            innerPadding = innerPadding,
            userName = viewModel.username.value,
            password = viewModel.password.value,
            onUserNameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onClickLogin = viewModel::login,
            onClickRegister = { navController.navigate(NavigationItem.Register.route) }
        )
    }
}

@Composable
fun LoginSection(
    innerPadding: PaddingValues,
    userName: String,
    password: String,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickLogin: () -> Unit,
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
            value = userName,
            onValueChange = { onUserNameChange(it) },
            label = stringResource(id = R.string.login_email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardType = KeyboardType.Email
        )
        TextFieldComponent(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = stringResource(id = R.string.login_password),
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isLast = true,
            keyboardType = KeyboardType.Password
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.login_forget_password),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            TextButton(onClick = { }) {
                Text(text = "Reset")
            }
        }
        Button(
            onClick = { onClickLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.login_button))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.login_for_register_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
            TextButton(onClick = {
                onClickRegister()
            }) {
                Text(
                    text = stringResource(id = R.string.login_for_register),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
//        Text(
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            text = "Version : ${BuildConfig.VERSION_NAME}",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
//            maxLines = 1,
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginSection(
        innerPadding = PaddingValues(),
        userName = "",
        password = "",
        onUserNameChange = {},
        onPasswordChange = {},
        onClickLogin = {},
        onClickRegister = {}
    )
}