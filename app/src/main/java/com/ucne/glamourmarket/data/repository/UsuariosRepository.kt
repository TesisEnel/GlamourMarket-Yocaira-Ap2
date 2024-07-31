package com.ucne.glamourmarket.data.repository

import retrofit2.HttpException
import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.UsuarioDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UsuariosRepository @Inject constructor(
    private val api: GlamourAPI
) {
    fun getUsuarios(): Flow<Resource<List<UsuarioDTO>>> = flow {
        try {
            emit(Resource.Loading())

            val usuarios = api.getUsuarios()

            emit(Resource.Success(usuarios))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    suspend fun registrarUsuarios(usuario: UsuarioDTO) = api.registrarUsuarios(usuario)

}