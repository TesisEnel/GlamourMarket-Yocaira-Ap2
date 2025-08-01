package com.ucne.glamourmarket.ui.theme.screams

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ucne.glamourmarket.R
import com.ucne.glamourmarket.presentation.navigation.Destination
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFD9FC3)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            Image(
                painter = painterResource(id = R.drawable.logoapp),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(350.dp)
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Inicio de sesión",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it },
                        label = { Text("Correo") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (!viewModel.emailError) {
                        Text(text = "El campo correo está vacío", color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (!viewModel.passwordError) {
                        Text(text = "El campo contraseña está vacío", color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { navController.navigate(Destination.RegistroUsuario.route) }) {
                            Text("Regístrate")
                        }

                        TextButton(onClick = { /* TODO */ }) {
                            Text("¿Has olvidado tu contraseña?")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (viewModel.ValidarLogin()) {
                                viewModel.singInWithEmailAndPassword(viewModel.email, viewModel.password) {
                                    navController.navigate(Destination.Home.route)
                                }
                            } else {
                                viewModel.loginError = true
                            }
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFD9FC3),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Inicio de sesión",
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        if (viewModel.loginError) {
            Snackbar(
                action = {
                    TextButton(
                        onClick = { viewModel.loginError = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = "OK")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Las credenciales no están registradas.",
                    color = Color.White
                )
            }
            LaunchedEffect(Unit) {
                delay(10000)
                viewModel.loginError = false
            }
        }
    }
}

