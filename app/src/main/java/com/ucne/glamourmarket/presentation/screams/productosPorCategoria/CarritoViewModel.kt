package com.ucne.glamourmarket.presentation.screams.productosPorCategoria

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.glamourmarket.data.dto.ProductosEnCarritoDTO
import com.ucne.glamourmarket.data.repository.CarritosRepository
import com.ucne.glamourmarket.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject



@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritosRepository: CarritosRepository
) : ViewModel() {
    var errorProductoYaEnCarrito by mutableStateOf(false)


    fun validarSiYaExisteEnCarrito(usuarioId: Int, productoId: Int) {
        viewModelScope.launch {
            val carrito = carritosRepository.getCarritoId(usuarioId)
            carrito.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val productos = resource.data?.productosEnCarrito ?: emptyList()
                        // Verificar si el producto ya está en la lista
                        errorProductoYaEnCarrito = productos.any { it.productoId == productoId }
                    }

                    else -> {
                        errorProductoYaEnCarrito = false
                    }
                }
            }
        }
    }

    fun agregarProductoACarrito(usuarioId: Int, productoId: Int, cantidad: Int) {
        viewModelScope.launch {
            carritosRepository.agregarProductoACarrito(usuarioId, productoId, cantidad)
        }
    }
}