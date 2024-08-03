package com.ucne.glamourmarket.presentation.screams.productosPorCategoria

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.data.repository.ProductosRepository
import com.ucne.glamourmarket.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductoListState(
    val isLoading: Boolean = false,
    val productos: List<ProductoDTO> = emptyList(),
    val producto: ProductoDTO? = null,
    val error: String = ""
)

@HiltViewModel
class ProductoCategoriaViewModel @Inject constructor(
    private val productosRepository: ProductosRepository,
) : ViewModel() {
    var id by mutableIntStateOf(0)
    var nombre by mutableStateOf("")
    var categoria by mutableStateOf("")
    var imagen by mutableStateOf("")
    var precio by mutableDoubleStateOf(0.0)
    var existencia by mutableIntStateOf(0)
    var impuesto by mutableIntStateOf(0)

    private val _ListProductos = MutableStateFlow(ProductoListState())
    val ListProductos: StateFlow<ProductoListState> = _ListProductos.asStateFlow()

    fun cargarProductosPorCategoria(categoriaSeleccionada: String){
        productosRepository.getProductosByCategoria(categoriaSeleccionada).onEach { result ->
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
}