package com.ucne.glamourmarket.ui.theme.screams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucne.glamourmarket.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductoCategoria() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFD9FC3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        ProductList()
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Aquí puedes agregar un icono para el menú si lo deseas
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Salir del usuario",
            modifier = Modifier
                .size(24.dp)
                .clickable { /* Acción para el ícono del menú */ }
        )

        Image(
            painter = painterResource(id = R.drawable.logo_solo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .clickable { /* Acción para el logo */ }
        )

        // Aquí puedes agregar un icono para el carrito si lo deseas
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Cart",
            modifier = Modifier
                .size(24.dp)
                .clickable {  }
        )

    }
}

@Composable
fun ProductList() {
    Column(
        modifier = Modifier
            .fillMaxSize()  // Asegúrate de que la columna ocupe todo el espacio disponible
            .background(Color(0xFFFFFFFF))
            .padding(8.dp)
    ) {
        SearchBar()
        Spacer(modifier = Modifier.height(10.dp))
        ProductCard(
            imageResource = R.drawable.perfume,
            name = "Nombre del producto:",
            description = "Descripción:",
            price = "$250"
        )
        ProductCard(
            imageResource = R.drawable.accesorios,
            name = "Nombre del producto:",
            description = "Descripción:",
            price = "$250",
            additionalInfo = "Existencia:"
        )
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Buscar") },
        trailingIcon = {
            IconButton(onClick = { /* Acción al hacer clic */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun ProductCard(
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6F2)),
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
                        text = "Agregar al carrito",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Acción al hacer clic */ },
                    Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    shape = RoundedCornerShape(size = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD08A),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Comprar ahora",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
