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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
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
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel
import com.ucne.glamourmarket.presentation.screams.productosPorCategoria.CarritoViewModel
import com.ucne.glamourmarket.presentation.screams.productosPorCategoria.ProductoCategoriaViewModel
import kotlinx.coroutines.delay


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
    if (carritoViewModel.errorProductoYaEnCarrito) {
        Snackbar(
            action = {
                TextButton(
                    onClick = { carritoViewModel.errorProductoYaEnCarrito = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                ) {
                    Text(text = "OK")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "El producto ya esta en el carrito",
                color = Color.Red
            )
        }
        LaunchedEffect(Unit) {
            delay(3000)
            carritoViewModel.errorProductoYaEnCarrito = false
        }
    }

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
                        description = producto.categoria,
                        price = producto.precio.toString()
                    )
                }
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

@SuppressLint("SuspiciousIndentation")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductCard(
    productoId: Int,
    imageUrl: String,
    name: String,
    description: String,
    price: String,
    additionalInfo: String? = null,
    carritoViewModel: CarritoViewModel = hiltViewModel(),
    usuarioViewModel: LoginViewModel = hiltViewModel()
) {
    // Recolectar el estado del usuario
    val usuarioState by usuarioViewModel.uiState.collectAsStateWithLifecycle()

    // Obtener el usuario actual de Firebase
    val currentUser = usuarioViewModel.auth.currentUser

    // Verificar si el usuario está autenticado
    if (currentUser != null) {
        // Buscar el usuario actual en el estado
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
                        onClick = {
                            // Asegurarse de que el usuario actual no sea nulo
                            if (usuarioActual?.id != null) {
                                carritoViewModel.validarSiYaExisteEnCarrito(usuarioActual.id, productoId)

                                if(!carritoViewModel.errorProductoYaEnCarrito)
                                carritoViewModel.agregarProductoACarrito(usuarioActual.id, productoId, 1)
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
    } else {
        println("No hay usuario autenticado.")
    }
}

