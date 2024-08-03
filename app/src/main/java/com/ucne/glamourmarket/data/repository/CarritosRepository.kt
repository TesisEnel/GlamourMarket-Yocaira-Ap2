package com.ucne.glamourmarket.data.repository

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.CarritoDTO
import com.ucne.glamourmarket.data.dto.CompraDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CarritosRepository @Inject constructor(
    private val api: GlamourAPI
) {
    suspend fun agregarProductoACarrito(usuarioId:Int, productoId: Int, cantidad: Int) {
        api.agregarProductoACarrito(usuarioId, productoId, cantidad)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getCarritoId(id: Int): Flow<Resource<CarritoDTO>> = flow {
        try {
            emit(Resource.Loading())

            val carrito =
                api.getCarritoByIdUsuario(id)

            emit(Resource.Success(carrito))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun eliminarProductoDelCarrito(usuarioId: Int, productoId: Int) : CarritoDTO? {
        return api.eliminarProductoDelCarrito(usuarioId, productoId).body()
    }
}