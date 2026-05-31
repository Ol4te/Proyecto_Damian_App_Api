package com.example.proyecto_damian_compose.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeliculaApi(
    @SerialName("title")
    val titulo: String,

    @SerialName("genre")
    val genero:String,

    @SerialName("description")
    val description: String,
    @SerialName("imageUrl")
    val imagen: String,
    @SerialName("country")
    val pais: String,
    @SerialName("directorFullName")
    val director:String? = null
){

}