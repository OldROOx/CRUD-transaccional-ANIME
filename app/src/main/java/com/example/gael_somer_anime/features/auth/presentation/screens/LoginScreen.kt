package com.example.gael_somer_anime.features.auth.presentation.screens

@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: (String) -> Unit, onNavToRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Bienvenido", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(onClick = { viewModel.login(username, password, onLoginSuccess) }) {
            Text("Entrar")
        }
        TextButton(onClick = onNavToRegister) {
            Text("Crear una cuenta")
        }
    }
}