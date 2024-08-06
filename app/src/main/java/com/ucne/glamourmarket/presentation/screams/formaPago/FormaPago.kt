package com.ucne.glamourmarket.ui.theme.screams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucne.glamourmarket.presentation.components.Header
import com.ucne.glamourmarket.presentation.navigation.Destination
import com.ucne.glamourmarket.presentation.screams.formaPago.FormaPagoViewModel


@Composable
fun FormaPago(navController: NavController, totalCompra: String, formaPagoViewModel: FormaPagoViewModel = hiltViewModel()) {
    val nombreTarjeta by formaPagoViewModel.nombreTarjeta
    val numeroTarjeta by formaPagoViewModel.numeroTarjeta
    val cvc by formaPagoViewModel.cvc
    val mesExpiracion by formaPagoViewModel.mesExpiracion
    val anioExpiracion by formaPagoViewModel.anioExpiracion

    val nombreTarjetaError by formaPagoViewModel.nombreTarjetaError
    val numeroTarjetaError by formaPagoViewModel.numeroTarjetaError
    val cvcError by formaPagoViewModel.cvcError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFD9FC3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(8.dp)
        ) {
            Text(
                text = "Forma de pago",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = nombreTarjeta,
                onValueChange = formaPagoViewModel::onNombreTarjetaChange,
                label = { Text("Nombre de la tarjeta") },
                isError = nombreTarjetaError != null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (nombreTarjetaError != null) {
                Text(
                    text = nombreTarjetaError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            OutlinedTextField(
                value = numeroTarjeta,
                onValueChange = formaPagoViewModel::onNumeroTarjetaChange,
                label = { Text("Número de la tarjeta") },
                isError = numeroTarjetaError != null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (numeroTarjetaError != null) {
                Text(
                    text = numeroTarjetaError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = mesExpiracion,
                    onValueChange = formaPagoViewModel::onMesExpiracionChange,
                    label = { Text("Mes de expiración") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .width(100.dp)
                )

                OutlinedTextField(
                    value = anioExpiracion,
                    onValueChange = formaPagoViewModel::onAnioExpiracionChange,
                    label = { Text("Año de expiración") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .width(100.dp)
                )
            }

            OutlinedTextField(
                value = cvc,
                onValueChange = formaPagoViewModel::onCvcChange,
                label = { Text("CVC") },
                isError = cvcError != null,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 16.dp)
            )
            if (cvcError != null) {
                Text(
                    text = cvcError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Detalles del pago:",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier.padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Total de la compra: $$totalCompra",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                )
                Spacer(modifier = Modifier.height(130.dp))

                Button(
                    onClick = {
                        if (formaPagoViewModel.validarCampos()) {
                            navController.navigate(Destination.Home.route)
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
                        text = "Pagar",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}