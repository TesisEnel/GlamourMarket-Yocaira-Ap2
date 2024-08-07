package com.ucne.glamourmarket.data.repository

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.ProductoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProductosRepository @Inject constructor(
    private val api: GlamourAPI
) {
    fun getProductosByCategoria(categoria: String): Flow<Resource<List<ProductoDTO>>> = flow {
        try {
            emit(Resource.Loading())

            val productos = api.getProductosByCategoria(categoria)

            emit(Resource.Success(productos))
        } catch (@SuppressLint("NewApi") e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getProductosEnCarritoPorUsuario(usuarioId: Int): Flow<Resource<List<ProductoDTO>>> = flow {
        try {
            emit(Resource.Loading())

            val productos = api.getProductosEnCarritoPorUsuario(usuarioId)

            emit(Resource.Success(productos))
        } catch (@SuppressLint("NewApi") e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getProductoById(id: Int): Flow<Resource<ProductoDTO>> = flow {
        try {
            emit(Resource.Loading())

            val producto =
                api.getProductoById(id)

            emit(Resource.Success(producto))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
}