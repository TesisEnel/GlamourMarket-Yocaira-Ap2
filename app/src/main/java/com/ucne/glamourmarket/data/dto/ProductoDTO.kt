package com.ucne.glamourmarket.data.dto

data class ProductoDTO(
    val id: Int? = null,
    val nombre: String = "",
    val categoria: String = "",
    val imagen: String = "",
    val precio: Double = 0.0,
    val existencia: Int? = null,
    val impuesto: Int? = null,
)
