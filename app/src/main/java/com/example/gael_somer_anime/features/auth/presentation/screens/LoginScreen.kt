package com.example.gael_somer_anime.features.auth.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gael_somer_anime.core.hardware.BiometricManager
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BiometricEntryPoint {
    fun getBiometricManager(): BiometricManager
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val user by viewModel.username.collectAsStateWithLifecycle()
    val pass by viewModel.password.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val authError by viewModel.authError.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val biometricManager = EntryPointAccessors.fromApplication(
        context.applicationContext,
        BiometricEntryPoint::class.java
    ).getBiometricManager()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

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

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.login { onLoginSuccess() } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            IconButton(
                onClick = {
                    val activity = context as? FragmentActivity
                    activity?.let {
                        if (biometricManager.canAuthenticate()) {
                            biometricManager.authenticate(
                                activity = it,
                                onSuccess = { viewModel.onBiometricSuccess { onLoginSuccess() } },
                                onError = { error -> viewModel.onBiometricError(error) }
                            )
                        } else {
                            viewModel.onBiometricError("Biometría no disponible")
                        }
                    }
                },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Huella Digital",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            TextButton(onClick = onNavToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }

        authError?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
