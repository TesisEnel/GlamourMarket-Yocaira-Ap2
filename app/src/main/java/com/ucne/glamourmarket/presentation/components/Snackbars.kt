package com.ucne.glamourmarket.presentation.components

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ucne.glamourmarket.presentation.screams.Carrito.CarritoViewModel
import kotlinx.coroutines.delay

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SnackbarErrorProductoYaEnCarrito(carritoViewModel: CarritoViewModel) {
    if (carritoViewModel.errorProductoYaEnCarrito) {
        Snackbar(
            action = {
                TextButton(
                    onClick = { carritoViewModel.errorProductoYaEnCarrito = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                ) {
                    Text(text = "OK")
                }
            },
            modifier = androidx.compose.ui.Modifier.padding(8.dp)
        ) {
            Text(
                text = "El producto ya está en el carrito",
                color = Color.Red
            )
        }
        LaunchedEffect(Unit) {
            delay(3000)
            carritoViewModel.errorProductoYaEnCarrito = false
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SnackbarProductoAgregadoConExito(carritoViewModel: CarritoViewModel) {
    if (carritoViewModel.productoAgregadoConExito) {
        Snackbar(
            action = {
                TextButton(
                    onClick = { carritoViewModel.productoAgregadoConExito = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                ) {
                    Text(text = "OK")
                }
            },
            modifier = androidx.compose.ui.Modifier.padding(8.dp)
        ) {
            Text(
                text = "Producto agregado al carrito con éxito",
                color = Color.Green
            )
        }
        LaunchedEffect(Unit) {
            delay(3000)
            carritoViewModel.productoAgregadoConExito = false
        }
    }
}