package com.example.proyecto_damian_compose.api

import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("users/signup")
    suspend fun crearCuenta(
        @Body usuario: UsuarioApi
    ): Map<String, String>

    @POST("users/login")
    suspend fun login(
        @Body usuario: UsuarioApi
    ): Map<String, String>
}