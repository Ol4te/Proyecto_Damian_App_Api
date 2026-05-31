package com.example.proyecto_damian_compose.api

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioApi(
    val email: String,
    val password: String
)