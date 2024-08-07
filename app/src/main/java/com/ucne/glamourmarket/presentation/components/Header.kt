package com.ucne.glamourmarket.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.glamourmarket.R
import com.ucne.glamourmarket.presentation.navigation.Destination
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel

@Composable
fun Header(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Salir del usuario",
            modifier = Modifier
                .size(24.dp)
                .clickable { viewModel.singOut {
                    navController.navigate(Destination.Login.route)
                }
                }
        )

        Image(
            painter = painterResource(id = R.drawable.logo_solo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .clickable { navController.navigate(Destination.Home.route) }
        )

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Cart",
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.navigate(Destination.ProductosEnCarritoScreen.route) }
        )

    }
}