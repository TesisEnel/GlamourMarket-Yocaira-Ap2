package com.ucne.glamourmarket.ui.theme.screams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ucne.glamourmarket.R
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.ui.presentation.navigation.Destination
import com.ucne.glamourmarket.ui.presentation.screams.productosPorCategoria.ProductoCategoriaViewModel

@Composable
fun Header(navController: NavController) {
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
                .clickable { navController.navigate(Destination.Login.route) }
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

@Composable
fun ProductoCategoria(navController: NavController, viewModel: ProductoCategoriaViewModel = hiltViewModel(), categoriaSeleccionada: String) {
    viewModel.cargarProductosPorCategoria(categoriaSeleccionada)
    val listaProductosByCategoria by viewModel.ListProductosPorCategoria.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFD9FC3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        ProductList(listaProductosByCategoria.productos, navController)
    }
}

@Composable
fun ProductList(productos: List<ProductoDTO>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(8.dp)
    ) {
        SearchBar()
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(productos) { producto ->
                ProductCard(
                    imageUrl = producto.imagen,
                    name = producto.nombre,
                    description = producto.categoria,
                    price = producto.precio.toString()
                )
            }
        }
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
    imageUrl: String,
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
                painter = rememberAsyncImagePainter(imageUrl),
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
