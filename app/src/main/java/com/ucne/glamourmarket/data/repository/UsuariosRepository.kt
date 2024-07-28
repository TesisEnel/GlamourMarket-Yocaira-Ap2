package com.ucne.glamourmarket.data.repository

import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.UsuarioDTO
import javax.inject.Inject

class UsuariosRepository @Inject constructor(
    private val api: GlamourAPI
) {
    suspend fun registrarUsuarios(usuario: UsuarioDTO) = api.registrarUsuarios(usuario)

}