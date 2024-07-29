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
import androidx.navigation.NavController
import com.ucne.glamourmarket.ui.presentation.navigation.Destination


@Composable
fun FormaPago(navController: NavController){
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
                .fillMaxSize()  // Asegúrate de que la columna ocupe todo el espacio disponible
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
                value = "",
                onValueChange = {},
                label = { Text("Nombre de la tarjeta") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Número de la tarjeta") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            PreviewExpirationDateSelector()

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("CVC") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 16.dp)
            )
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
                    .fillMaxSize()  // Asegúrate de que la columna ocupe todo el espacio disponible
                    .background(Color(0xFFFFFFFF))
                    .padding(16.dp)
            )
            {
                Text(
                    text = "Producto:",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Itbis:",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Total de la compra:",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                )
                Spacer(modifier = Modifier.height(130.dp))

                Button(
                    onClick = {
                        navController.navigate(Destination.Home.route)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpirationDateSelector() {
    // Estado para el mes
    var monthExpanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("MM") }
    val months = listOf("01 - Enero", "02 - Febrero", "03 - Marzo", "04 - Abril", "05 - Mayo", "06 - Junio",
        "07 - Julio", "08 - Agosto", "09 - Septiembre", "10 - Octubre", "11 - Noviembre", "12 - Diciembre")

    // Estado para el año
    var yearExpanded by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf("AAAA") }
    val years = listOf("2024", "2025", "2026", "2027", "2028", "2029", "2030")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Expiración",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(end = 8.dp)
        )

        // Selector de Mes
        ExposedDropdownMenuBox(
            expanded = monthExpanded,
            onExpandedChange = { monthExpanded = !monthExpanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedMonth,
                onValueChange = {},
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .width(125.dp)
                    .clickable { monthExpanded = true }
            )
            ExposedDropdownMenu(
                expanded = monthExpanded,
                onDismissRequest = { monthExpanded = false }
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(month) },
                        onClick = {
                            selectedMonth = month
                            monthExpanded = false
                        }
                    )
                }
            }
        }

        // Selector de Año
        ExposedDropdownMenuBox(
            expanded = yearExpanded,
            onExpandedChange = { yearExpanded = !yearExpanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedYear,
                onValueChange = {},
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .width(120.dp)
                    .clickable { yearExpanded = true }
            )
            ExposedDropdownMenu(
                expanded = yearExpanded,
                onDismissRequest = { yearExpanded = false }
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year) },
                        onClick = {
                            selectedYear = year
                            yearExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpirationDateSelector() {
    ExpirationDateSelector()
}
