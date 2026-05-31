package com.example.proyecto_damian_compose.controller

import com.example.proyecto_damian_compose.api.UserApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlin.jvm.java

object RetrofitConfig{
    private val contentType =
        "application/json; charset=utf-8"
            .toMediaType()

    private val okHttpClient =
        OkHttpClient.Builder()
            .build()

    private const val BASE_URL= "https://moviesrestapi-production.up.railway.app/api/v1/"


    private val json = Json {

        ignoreUnknownKeys = true

    }

    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(contentType)
            )
            .build()
    }

    fun userApiService(): UserApiService{
        return retrofit.create(UserApiService::class.java)
    }

    fun peliculaApiService(): PeliculaApiService{
        return retrofit.create(PeliculaApiService::class.java)
    }




}