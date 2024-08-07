package com.ucne.glamourmarket.presentation.screams.Carrito

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.glamourmarket.data.dto.CarritoDTO
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.data.dto.ProductosEnCarritoDTO
import com.ucne.glamourmarket.data.repository.CarritosRepository
import com.ucne.glamourmarket.data.repository.ProductosRepository
import com.ucne.glamourmarket.data.repository.Resource
import com.ucne.glamourmarket.presentation.screams.productosPorCategoria.ProductoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ProductosEnCarritoListState(
    val isLoading: Boolean = false,
    val ProductosEnCarrito: List<ProductosEnCarritoDTO> = emptyList(),
    val ProductoEnCarrito: ProductosEnCarritoDTO? = null,
    val error: String = ""
)
@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritosRepository: CarritosRepository,
    private val productosRepository: ProductosRepository
) : ViewModel() {
    var carrito by mutableStateOf(CarritoDTO())
    var errorProductoYaEnCarrito by mutableStateOf(false)
    var productoAgregadoConExito by mutableStateOf(false)

    private val _ListProductos = MutableStateFlow(ProductoListState())
    val ListProductos: StateFlow<ProductoListState> = _ListProductos.asStateFlow()

    private val _ListProductosEnCarrito = MutableStateFlow(ProductosEnCarritoListState())
    val ListProductosEnCarrito: StateFlow<ProductosEnCarritoListState> = _ListProductosEnCarrito.asStateFlow()

    fun cargarProductosPorCarrito(usuarioId: Int){
        carritosRepository.getDetalleCarritoProductos(usuarioId).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _ListProductosEnCarrito.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _ListProductosEnCarrito.update { it.copy(ProductosEnCarrito = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _ListProductosEnCarrito.update { it.copy(error = result.message ?: "Error desconocido") }
                }
                else -> {}
            }

        }.launchIn(viewModelScope)
    }

    suspend fun validarSiYaExisteEnCarrito(usuarioId: Int, productoId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val carrito = carritosRepository.getCarritoByIdUsuario(usuarioId)
            var productoExiste = false
            carrito.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val productos = resource.data?.productosEnCarrito ?: emptyList()
                        productoExiste = productos.any { it.productoId == productoId }
                    }
                    else -> {
                        productoExiste = false
                    }
                }
            }
            productoExiste
        }
    }

    fun cargarProductosEnCarritoPorUsuario(usuarioId: Int){
        productosRepository.getProductosEnCarritoPorUsuario(usuarioId).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _ListProductos.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _ListProductos.update { it.copy(productos = result.data ?: emptyList()) }
                }

                is Resource.Error -> {
                    _ListProductos.update { it.copy(error = result.message ?: "Error desconocido") }
                }
                else -> {}
            }

        }.launchIn(viewModelScope)
    }

    fun getCarritoByUsuarioId(usuariId: Int){
        carritosRepository.getCarritoByIdUsuario(usuariId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    carrito = result.data!!
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun agregarProductoACarrito(usuarioId: Int, productoId: Int, cantidad: Int) {
        viewModelScope.launch {
            val existe = validarSiYaExisteEnCarrito(usuarioId, productoId)
            errorProductoYaEnCarrito = existe
            if (!existe) {
                carritosRepository.agregarProductoACarrito(usuarioId, productoId, cantidad)
                productoAgregadoConExito = true
            }
        }
    }

    fun eliminarProductoACarrito(usuarioId: Int, productoId: Int) {
        viewModelScope.launch {
            carritosRepository.eliminarProductoDelCarrito(usuarioId, productoId)
            cargarProductosPorCarrito(usuarioId)
        }
    }
}
