package com.example.proyecto_damian_compose.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.proyecto_damian_compose.modelo.DatosDemo
import com.example.proyecto_damian_compose.modelo.Pelicula
import com.example.proyecto_damian_compose.modelo.Usuario
import com.example.proyecto_damian_compose.R
import com.example.proyecto_damian_compose.componentes.CampoReutilizable


@Composable
fun AgregarPelicula(usuario : Usuario, cerrarMenu: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth().padding(24.dp)
                .background(
                    shape = RoundedCornerShape(30.dp),
                    color = MaterialTheme.colorScheme.surface
                ), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            var tituloPelicula by remember { mutableStateOf("") }
            var director by remember { mutableStateOf("") }
            var genero by remember { mutableStateOf("") }
            var imagen by remember { mutableStateOf("") }
            var score by remember { mutableStateOf("") }

            var errorTitulo by remember { mutableStateOf(false) }
            var errorDirector by remember { mutableStateOf(false) }
            var errorGenero by remember { mutableStateOf(false) }
            var errorScore by remember { mutableStateOf(false) }

            fun validarDatos(): Boolean {
                tituloPelicula = tituloPelicula.trim()
                director = director.trim()
                genero = genero.trim()
                score = score.trim()

                errorTitulo = false
                errorDirector = false
                errorGenero = false
                errorScore = false

                var esValido = true //Bandera

                if (tituloPelicula.isBlank()) {
                    errorTitulo = true
                    esValido = false
                }

                if (director.isBlank()) {
                    errorDirector = true
                    esValido = false
                }

                if (genero.isBlank()) {
                    errorGenero = true
                    esValido = false
                }

                if (score.toDoubleOrNull() == null) {
                    errorScore = true
                    esValido = false
                }
                return esValido
            }

            Row(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.weight(2f).padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CampoReutilizable(valor = tituloPelicula, cambioValor = {tituloPelicula = it}, placeholder = stringResource(R.string.campo_titulo),
                        error = errorTitulo)
                    CampoReutilizable(valor = director, cambioValor = {director = it}, placeholder = stringResource(R.string.campo_director), error = errorDirector)
                    CampoReutilizable(valor = genero, cambioValor = {genero = it}, placeholder = stringResource(R.string.campo_categoria), error= errorGenero)
                    CampoReutilizable(valor = score, cambioValor = {score = it}, placeholder = stringResource(R.string.campo_score_placeholder), error= errorScore)


                }
                Column(
                    Modifier.weight(1f).padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth().aspectRatio(2f/ 3f),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {}) {
                        //TODO seleccionar una imagen del sistema
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(R.drawable.imagen_renombrar),
                                contentDescription = stringResource(R.string.imagen_pelicula_desc),
                                Modifier.size(48.dp)
                            )
                        }
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp).fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.flecha_volver),
                    Modifier.size(48.dp).clickable { cerrarMenu() })
                Spacer(Modifier.width(50.dp))
                Button(modifier = Modifier.width(150.dp), onClick = {
                    if (validarDatos()) {
                        val peliculaNueva = Pelicula(
                            DatosDemo.generarIdPelicula(),
                            tituloPelicula,
                            genero,
                            director,
                            score.toDouble(),
                            R.drawable.imagen_renombrar
                        ) //Usamos imagen por defecto
                        DatosDemo.peliculas.add(peliculaNueva)
                        usuario.listaPeliculas.add(peliculaNueva)
                        cerrarMenu()
                    }
                }) {
                    Text(
                        stringResource(R.string.boton_agregar),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }


        }
    }
}


@PreviewScreenSizes
@Composable
fun AgregarPeliculaPreview(){
    AgregarPelicula(usuario= Usuario(DatosDemo.generarIdUsuario(),nombre= "Demo", apellido = "Apellido", email = "demo@gmail.com", contraseña = "1234"),cerrarMenu = {})}



