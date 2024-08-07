package com.ucne.glamourmarket.data.repository

import android.annotation.SuppressLint
import android.net.http.HttpException
import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.dto.CompraDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ComprasRepository @Inject constructor(
    private val api: GlamourAPI
) {
    suspend fun comprarTodoEnCarrito(carritoId: Int) = api.comprarTodoEnCarrito(carritoId)
}