package com.example.proyecto_damian_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.proyecto_damian_compose.modelo.DatosDemo
import com.example.proyecto_damian_compose.pantallas.PantallaGaleria
import com.example.proyecto_damian_compose.pantallas.PantallaLogin
import com.example.proyecto_damian_compose.pantallas.PantallaPrincipal
import com.example.proyecto_damian_compose.ui.theme.Proyecto_Damian_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_Damian_ComposeTheme {

                Navegacion()
            }
        }
    }
}

