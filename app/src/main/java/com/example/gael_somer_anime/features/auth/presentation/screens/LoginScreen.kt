package com.example.gael_somer_anime.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    factory: LoginViewModelFactory,
    onLoginSuccess: () -> Unit,
    onNavToRegister: () -> Unit
) {
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val user by viewModel.username.collectAsStateWithLifecycle()
    val pass by viewModel.password.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val authError by viewModel.authError.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = user,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.login { onLoginSuccess() }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            TextButton(onClick = onNavToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }

        authError?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}