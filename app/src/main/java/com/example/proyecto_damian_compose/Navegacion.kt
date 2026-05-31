package com.example.proyecto_damian_compose

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.proyecto_damian_compose.modelo.DatosDemo
import com.example.proyecto_damian_compose.pantallas.PantallaGaleria
import com.example.proyecto_damian_compose.pantallas.PantallaLogin
import com.example.proyecto_damian_compose.pantallas.PantallaPrincipal

@Composable
fun Navegacion() {
    val backStack = rememberNavBackStack(Login)

    NavDisplay(backStack = backStack, entryProvider = entryProvider {
        entry<Login>{
            PantallaLogin(loginCorrecto = {email ->
                backStack.removeLastOrNull()
                backStack.add(Principal(email))})

        }

        entry<Principal> {
            val usuario = DatosDemo.usuarios.find { u -> u.email == it.email}
            usuario?.let {u -> PantallaPrincipal(usuario = u,
                abrirGaleria = {backStack.add(Galeria(u.email))}) }
        }

        entry<Galeria> {
            PantallaGaleria(
                usuario = DatosDemo.usuarios.find{ u -> u.email == it.email}!!,
                volver = {backStack.removeLastOrNull()}
            )
        }

    }

    )
}