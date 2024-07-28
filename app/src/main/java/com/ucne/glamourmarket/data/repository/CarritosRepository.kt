package com.ucne.glamourmarket.data.repository

import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.CarritoDTO
import javax.inject.Inject

class CarritosRepository @Inject constructor(
    private val api: GlamourAPI
) {
    suspend fun agregarProductoACarrito(usuarioId:Int, productoId: Int, cantidad: Int) {
        api.agregarProductoACarrito(usuarioId, productoId, cantidad)
    }

    suspend fun eliminarProductoDelCarrito(usuarioId: Int, productoId: Int) : CarritoDTO? {
        return api.eliminarProductoDelCarrito(usuarioId, productoId).body()
    }
}