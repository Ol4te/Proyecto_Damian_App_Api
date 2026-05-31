package com.example.proyecto_damian_compose.controller

import com.example.proyecto_damian_compose.modelo.Pelicula
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PeliculaApiService{
    @GET("movies")
    suspend fun obtenerTodas():List<Pelicula>

    @GET("movies/{id}")
    suspend fun obtenerPorId(@Path("id") id:String): Pelicula

    @POST("movies")
    suspend fun agregarPelicula(@Body p: Pelicula): Pelicula
}