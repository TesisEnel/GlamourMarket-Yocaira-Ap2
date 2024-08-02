package com.ucne.glamourmarket.ui.theme.screams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ucne.glamourmarket.R
import com.ucne.glamourmarket.presentation.components.Header
import com.ucne.glamourmarket.presentation.navigation.Destination


@Composable
fun CarritoCompra(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFD9FC3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header(navController)
        Spacer(modifier = Modifier.height(16.dp))
        ListaProductoCarrito(navController)
    }
}

@Composable
fun ListaProductoCarrito(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()  // Asegúrate de que la columna ocupe todo el espacio disponible
            .background(Color(0xFFFFFFFF))
            .padding(8.dp)

    ) {
        Text(
            text = "Carrito de compra",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(16.dp)
        )


        ProductoCarrito(
            imageResource = R.drawable.perfume,
            name = "Nombre del producto:",
            description = "Descripción:",
            price = "$250",
        )
        ProductoCarrito(
            imageResource = R.drawable.accesorios,
            name = "Nombre del producto:",
            description = "Descripción:",
            price = "$250",
        )
        Text(
            text = "Total: $500",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                navController.navigate(Destination.PagarScreen.route)
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
                text = "Comprar",
                color = Color.Black,
                fontSize = 18.sp
            )
        }

    }
}

@Composable
fun ProductoCarrito(
    imageResource: Int,
    name: String,
    description: String,
    price: String,
    additionalInfo: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { /* Acción al hacer clic */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFCFB)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Producto",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop // O ContentScale.Fit según tu necesidad
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                additionalInfo?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Text(
                    text = "Precio: $price",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* Acción al hacer clic */ },
                        Modifier
                            .fillMaxWidth()
                            .height(35.dp),
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFD9FC3),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Eliminar",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
            }
        }
    }

}
