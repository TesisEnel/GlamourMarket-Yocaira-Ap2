package com.ucne.glamourmarket.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String,val icon: ImageVector, val title: String) {
    object Login : Destination(
        route = "Login",icon = Icons.Filled.Home, title = "Login"
    )

    object RegistroUsuario : Destination(
        route = "RegistroUsuario",icon = Icons.Filled.Home, title = "RegistroUsuario"
    )

    object Home : Destination(
        route = "Home",icon = Icons.Filled.Home, title = "Home"
    )

    object ProductosPorCategoriaScreen : Destination(
        route = "ProductosPorCategoriaScreen",icon = Icons.Filled.Home, title = "ProductosPorCategoriaScreen"
    )

    object ProductosEnCarritoScreen : Destination(
        route = "ProductosEnCarritoScreen",icon = Icons.Filled.Home, title = "ProductosEnCarritoScreen"
    )

    object PagarScreen : Destination(
        route = "PagarScreen",icon = Icons.Filled.Home, title = "PagarScreen"
    )

    companion object {
        val toList = listOf(Home)
    }
}