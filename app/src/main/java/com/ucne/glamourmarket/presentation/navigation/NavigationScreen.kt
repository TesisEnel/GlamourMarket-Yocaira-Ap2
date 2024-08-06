package com.ucne.glamourmarket.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.ucne.glamourmarket.ui.theme.screams.CarritoCompra
import com.ucne.glamourmarket.ui.theme.screams.FormaPago
import com.ucne.glamourmarket.ui.theme.screams.HomeScream
import com.ucne.glamourmarket.ui.theme.screams.LoginScreen
import com.ucne.glamourmarket.ui.theme.screams.ProductoCategoria
import com.ucne.glamourmarket.ui.theme.screams.RegisterScreen


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppScreen() {
    val navController = rememberNavController()

    Scaffold(
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavigation(navController = navController)
            }
        }
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            Destination.Login.route
        } else {
            Destination.Home.route
        }
    ) {
        composable(Destination.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Destination.RegistroUsuario.route) {
            RegisterScreen(navController)
        }
        composable(Destination.Home.route) {
            HomeScream(navController = navController)
        }
        composable(
            Destination.ProductosPorCategoriaScreen.route + "/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { capturar ->
            val categoria = capturar.arguments?.getString("categoria") ?: ""
            ProductoCategoria(navController = navController, categoriaSeleccionada = categoria)
        }
        composable(Destination.ProductosEnCarritoScreen.route) {
            CarritoCompra(navController = navController)
        }
        composable(Destination.PagarScreen.route + "/{total}",
            arguments = listOf(navArgument("total") { type = NavType.StringType })
            ) {capturar ->
            val total = capturar.arguments?.getString("total") ?: ""
            FormaPago(navController = navController, total)
        }
    }
}