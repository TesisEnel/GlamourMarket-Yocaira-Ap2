package com.ucne.glamourmarket.ui.theme.screams


import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ucne.glamourmarket.data.dto.ProductosEnCarritoDTO
import com.ucne.glamourmarket.presentation.components.Header
import com.ucne.glamourmarket.presentation.navigation.Destination
import com.ucne.glamourmarket.presentation.screams.Carrito.CarritoViewModel
import com.ucne.glamourmarket.presentation.screams.login.LoginViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ListaProductoCarrito(navController: NavController, usuarioViewModel: LoginViewModel = hiltViewModel(), carritoViewModel: CarritoViewModel = hiltViewModel()) {
    val currentUser = usuarioViewModel.auth.currentUser
    val usuarioState by usuarioViewModel.uiState.collectAsStateWithLifecycle()
    val carritoState by carritoViewModel.ListProductosEnCarrito.collectAsStateWithLifecycle()
    val productosEnCarritoState by carritoViewModel.ListProductos.collectAsStateWithLifecycle()

    if (currentUser != null) {
        val usuarioActual = usuarioState.usuarios.singleOrNull {
            it.email == currentUser.email
        }

        if (usuarioActual != null) {
            usuarioActual.id?.let { carritoViewModel.cargarProductosPorCarrito(it) }
            usuarioActual.id?.let { carritoViewModel.cargarProductosEnCarritoPorUsuario(it) }

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

                if (carritoState.ProductosEnCarrito.isEmpty()) {
                    Text(
                        text = "Tu carrito está vacío",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            fontFamily = FontFamily.SansSerif
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(carritoState.ProductosEnCarrito) { productoEnCarrito ->
                            val producto = productosEnCarritoState.productos.firstOrNull { it.id == productoEnCarrito.productoId }
                            if (producto != null) {
                                ProductoCarrito(
                                    productoEnCarrito = productoEnCarrito,
                                    producto = producto
                                )
                            }
                        }
                    }

                    // Calcular el total
                    val total = carritoState.ProductosEnCarrito.sumOf { productoEnCarrito ->
                        val producto = productosEnCarritoState.productos.firstOrNull { it.id == productoEnCarrito.productoId }
                        if (producto != null) {
                            productoEnCarrito.cantidad?.let { cantidad ->
                                cantidad * producto.precio
                            } ?: 0.0
                        } else {
                            0.0
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Total: $$total",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.SansSerif
                            ),
                            modifier = Modifier.padding(16.dp)
                        )

                        Button(
                            onClick = {
                                navController.navigate(Destination.PagarScreen.route + "/${total}")
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
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProductoCarrito(
    productoEnCarrito: ProductosEnCarritoDTO,
    producto: ProductoDTO,
    additionalInfo: String? = null,
    usuarioViewModel: LoginViewModel = hiltViewModel(),
    carritoViewModel: CarritoViewModel = hiltViewModel()
) {
    var isEditing by remember { mutableStateOf(false) }
    var cantidad by mutableStateOf(productoEnCarrito.cantidad)
    var cantidadASumar by remember { mutableIntStateOf(0) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6F2)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = producto.imagen),
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
                    text = producto.nombre,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Existencia: ${producto.existencia}",
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
                    text = "Precio: ${producto.precio}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                OutlinedTextField(
                    value = if (isEditing) cantidadASumar.toString() else cantidad.toString(),
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            if (isEditing) {
                                cantidadASumar = newValue
                            } else {
                                cantidad = newValue
                            }
                        }
                    },
                    label = { Text(if (isEditing) "Cantidad a sumar" else "Cantidad") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (isEditing) {
                            // Lógica para guardar los cambios y agregar al carrito
                            val usuarioActual = usuarioViewModel.uiState.value.usuarios.singleOrNull {
                                it.email == usuarioViewModel.auth.currentUser?.email
                            }
                            usuarioActual?.id?.let { userId ->
                                cantidadASumar.let { cantidadASumar ->
                                    carritoViewModel.agregarProductoACarrito(userId, producto.id!!, cantidadASumar, true)
                                    cantidad = cantidad?.plus(cantidadASumar) // Actualiza la cantidad en la UI
                                    isEditing = false // Salir del modo de edición
                                }
                            }
                        } else {
                            isEditing = true // Entrar en modo de edición
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    shape = RoundedCornerShape(size = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEditing) Color(0xFF4CAF50) else Color(0xFFFFD08A),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isEditing) "Guardar" else "Editar", // Cambia el texto del botón
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val usuarioActual = usuarioViewModel.uiState.value.usuarios.singleOrNull {
                            it.email == usuarioViewModel.auth.currentUser?.email
                        }
                        usuarioActual?.id?.let {
                            carritoViewModel.eliminarProductoACarrito(it, producto.id!!)
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
