package com.example.proyecto_damian_compose

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object Login: NavKey

@Serializable
data class Principal(val email: String): NavKey

@Serializable
data class Galeria(val email: String) : NavKey