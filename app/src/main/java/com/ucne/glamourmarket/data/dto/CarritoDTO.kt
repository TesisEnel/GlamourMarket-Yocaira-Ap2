package com.ucne.glamourmarket.data.dto

data class CarritoDTO(
    val id: Int? = null,
    val usuarioId: Int? = null,
    val productosEnCarrito: MutableList<ProductosEnCarritoDTO> = mutableListOf()
)
