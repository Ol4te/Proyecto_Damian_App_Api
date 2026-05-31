package com.example.proyecto_damian_compose.controller

import com.example.proyecto_damian_compose.api.PeliculaApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PeliculaApiService {

    @GET("movies")
    suspend fun obtenerTodas(
        @Header("Authorization") token: String
    ): List<PeliculaApi>

    @GET("movies/{id}")
    suspend fun obtenerPorId(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): PeliculaApi

    @POST("movies")
    suspend fun agregarPelicula(
        @Header("Authorization") token: String,
        @Body pelicula: PeliculaApi
    ): PeliculaApi

    @DELETE("movies/{id}")
    suspend fun borrarPelicula(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )


}