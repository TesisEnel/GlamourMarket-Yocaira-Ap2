package com.ucne.glamourmarket.ui.theme.screams

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.presentation.components.Header
import com.ucne.glamourmarket.presentation.components.SearchBar
import com.ucne.glamourmarket.presentation.components.SnackbarErrorProductoYaEnCarrito
import com.ucne.glamourmarket.presentation.components.SnackbarProductoAgregadoConExito
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel
import com.ucne.glamourmarket.presentation.screams.Carrito.CarritoViewModel
import com.ucne.glamourmarket.presentation.screams.productosPorCategoria.ProductoCategoriaViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductoCategoria(
    navController: NavController,
    viewModel: ProductoCategoriaViewModel = hiltViewModel(),
    categoriaSeleccionada: String,
) {
    LaunchedEffect(categoriaSeleccionada) {
        viewModel.cargarProductosPorCategoria(categoriaSeleccionada)
    }
    val listaProductosByCategoria by viewModel.ListProductos.collectAsStateWithLifecycle()
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
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductList(productos: List<ProductoDTO>, navController: NavController, carritoViewModel: CarritoViewModel = hiltViewModel()) {
    SnackbarErrorProductoYaEnCarrito()
    SnackbarProductoAgregadoConExito()

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
                producto.id?.let {
                    ProductCard(
                        productoId = it,
                        imageUrl = producto.imagen,
                        name = producto.nombre,
                        stock = producto.existencia.toString(),
                        price = producto.precio.toString()
                    )
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation", "UnrememberedMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductCard(
    productoId: Int,
    imageUrl: String,
    name: String,
    stock: String,
    price: String,
    additionalInfo: String? = null,
    carritoViewModel: CarritoViewModel = hiltViewModel(),
    usuarioViewModel: LoginViewModel = hiltViewModel()
) {
    // Recolectar el estado del usuario
    val usuarioState by usuarioViewModel.uiState.collectAsStateWithLifecycle()
    var cantidad by mutableStateOf(1)

    // Obtener el usuario actual de Firebase
    val currentUser = usuarioViewModel.auth.currentUser

    // Verificar si el usuario está autenticado
    if (currentUser != null) {
        // Buscar el usuario actual en el estado
        val usuarioActual = usuarioState.usuarios.singleOrNull {
            it.email == currentUser.email
        }

        if(usuarioActual != null) {
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
                            text = "Existencia: $stock",
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

                        OutlinedTextField(
                            value = cantidad.toString(),
                            onValueChange = {
                                val newValue = it.toIntOrNull()
                                if (newValue != null) {
                                    cantidad = newValue
                                }
                            },
                            label = { Text("Cantidad") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = {
                                // Asegurarse de que el usuario actual no sea nulo
                                if (usuarioActual.id != null) {
                                    carritoViewModel.agregarProductoACarrito(usuarioActual.id, productoId, cantidad)
                                    cantidad = 1
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
    } else {
        println("No hay usuario autenticado.")
    }
}
