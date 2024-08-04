package com.ucne.glamourmarket.presentation.screams.Carrito

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.glamourmarket.data.repository.CarritosRepository
import com.ucne.glamourmarket.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritosRepository: CarritosRepository
) : ViewModel() {
    var errorProductoYaEnCarrito by mutableStateOf(false)
    var productoAgregadoConExito by mutableStateOf(false)

    suspend fun validarSiYaExisteEnCarrito(usuarioId: Int, productoId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val carrito = carritosRepository.getCarritoId(usuarioId)
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
}
