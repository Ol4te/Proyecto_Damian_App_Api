package com.example.proyecto_damian_compose.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeliculaApi(

    @SerialName("id")
    val id: String? = null,

    @SerialName("title")
    val titulo: String = "",

    @SerialName("genre")
    val genero: String = "",

    @SerialName("description")
    val description: String? = null,

    @SerialName("imageUrl")
    val imagen: String? = null,

    @SerialName("country")
    val pais: String? = null,

    @SerialName("directorFullname")
    val director: String? = null
)