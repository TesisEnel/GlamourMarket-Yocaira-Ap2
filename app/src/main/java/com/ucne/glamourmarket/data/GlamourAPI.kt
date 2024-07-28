package com.ucne.glamourmarket.data

import com.ucne.glamourmarket.data.dto.CarritoDTO
import com.ucne.glamourmarket.data.dto.CompraDTO
import com.ucne.glamourmarket.data.dto.ProductoDTO
import com.ucne.glamourmarket.data.dto.UsuarioDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GlamourAPI {
    //Usuarios
    @POST("api/Usuarios")
    suspend fun registrarUsuarios(@Body usuarioDTO: UsuarioDTO): Response<UsuarioDTO>

    @GET("api/Productos/ProductosByCategory")
    suspend fun getProductosByCategoria():List<ProductoDTO>

    @GET("api/Productos/ProductosEnCarritoPorUsuario")
    suspend fun getProductosEnCarritoPorUsuario(usuarioId:Int):List<ProductoDTO>

    @PUT("api/Carritos/AgregarProductoACarrito")
    suspend fun agregarProductoACarrito(usuarioId:Int, productoId: Int, cantidad: Int): Response<Unit>

    @DELETE("api/Carritos/EliminarProductoDelCarrito")
    suspend fun eliminarProductoDelCarrito(usuarioId: Int, productoId: Int): Response<CarritoDTO>

    @POST("api/Compras/{carritoId}/ComprarTodoEnCarrito")
    suspend fun comprarTodoEnCarrito(carritoId: Int): Response<CompraDTO>

    @GET("api/Compras/{id}")
    suspend fun getComprasById(@Path("id") id: Int): CompraDTO



}