package com.example.proyecto_damian_compose.modelo


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Usuario(val id:Int, val nombre: String, val apellido: String, var email: String, var contraseña:String, val listaPeliculas : SnapshotStateList<Pelicula> = mutableStateListOf()) {
//Cambie de val lista_Peliculas : MutableList<Pelicula> = mutableListOf() -> SnapshotStateList<Pelicula> = mutableStateofList
    //Lista de peliculas para cada usuario


}