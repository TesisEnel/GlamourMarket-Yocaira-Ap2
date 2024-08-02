package com.ucne.glamourmarket.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.ucne.glamourmarket.ui.theme.screams.CarritoCompra
import com.ucne.glamourmarket.ui.theme.screams.FormaPago
import com.ucne.glamourmarket.ui.theme.screams.HomeScream
import com.ucne.glamourmarket.ui.theme.screams.LoginScreen
import com.ucne.glamourmarket.ui.theme.screams.ProductoCategoria
import com.ucne.glamourmarket.ui.theme.screams.RegisterScreen


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

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController,
        startDestination = if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            Destination.Login.route
        } else{
            Destination.Home.route
        }
    ){
        composable(Destination.Login.route){
            LoginScreen(navController = navController)
        }

        composable(Destination.RegistroUsuario.route){
            RegisterScreen(navController)
        }

        composable(Destination.Home.route){
            HomeScream(navController = navController)
        }

        composable(
            Destination.ProductosPorCategoriaScreen.route + "/{categoria}", arguments = listOf(
            navArgument("categoria") { type = NavType.StringType})
        ){ capturar ->
            val categoria = capturar.arguments?.getString("categoria") ?: ""
            ProductoCategoria(navController = navController, categoriaSeleccionada = categoria)
        }

        composable(Destination.ProductosEnCarritoScreen.route){
            CarritoCompra(navController = navController)
        }

        composable(Destination.PagarScreen.route){
            FormaPago(navController = navController)
        }
    }

}