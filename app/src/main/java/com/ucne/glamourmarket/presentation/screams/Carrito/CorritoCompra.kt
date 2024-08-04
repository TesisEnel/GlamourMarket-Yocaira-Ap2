package com.ucne.glamourmarket.ui.theme.screams

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.presentation.components.Header
import com.ucne.glamourmarket.presentation.navigation.Destination
import com.ucne.glamourmarket.presentation.screams.Carrito.CarritoViewModel
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel
import com.ucne.glamourmarket.presentation.screams.productosPorCategoria.ProductoCategoriaViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun CarritoCompra(navController: NavController, carritoViewModel: CarritoViewModel = hiltViewModel(), usuarioViewModel: LoginViewModel = hiltViewModel()) {
    val usuarioState by usuarioViewModel.uiState.collectAsStateWithLifecycle()
    val currentUser = usuarioViewModel.auth.currentUser

    if(currentUser != null){
        val usuarioActual = usuarioState.usuarios.singleOrNull {
            it.email == currentUser.email
        }

        if (usuarioActual != null) {
            usuarioActual.id?.let { carritoViewModel.cargarProductosEnCarritoPorUsuario(it) }
        }

        val productosState by carritoViewModel.ListProductos.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFD9FC3)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Header(navController)
            Spacer(modifier = Modifier.height(16.dp))
            ListaProductoCarrito(navController, productosState.productos)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ListaProductoCarrito(navController: NavController, productos: List<ProductoDTO>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Assign weight to take the remaining space
        ) {
            items(productos) { producto ->
                producto.id?.let {
                    ProductoCarrito(
                        productoId = it,
                        imageResource = producto.imagen,
                        name = producto.nombre,
                        categoria = producto.categoria,
                        price = producto.precio.toString()
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductoCarrito(
    productoId: Int,
    imageResource: String,
    name: String,
    categoria: String,
    price: String,
    additionalInfo: String? = null,
    carritoViewModel: CarritoViewModel = hiltViewModel(),
    usuarioViewModel: LoginViewModel = hiltViewModel()
) {
    val usuarioState by usuarioViewModel.uiState.collectAsStateWithLifecycle()
    val currentUser = usuarioViewModel.auth.currentUser

    if(currentUser != null){
        val usuarioActual = usuarioState.usuarios.singleOrNull {
            it.email == currentUser.email
        }
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
                    painter = rememberAsyncImagePainter(model = imageResource),
                    contentDescription = "Producto",
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
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
                        text = categoria,
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
                        onClick = {
                            if (usuarioActual != null) {
                                usuarioActual.id?.let {
                                    carritoViewModel.eliminarProductoACarrito(
                                        it, productoId)
                                }
                            }
                        },
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
}
