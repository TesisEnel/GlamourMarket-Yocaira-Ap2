package com.ucne.glamourmarket.data.dto

data class CompraDTO(
    val id: Int? = null,
    val usuarioId: Int? = null,
    val total: Int? = null,
    val compraProductos: MutableList<CompraProductosDTO> = mutableListOf()
)
