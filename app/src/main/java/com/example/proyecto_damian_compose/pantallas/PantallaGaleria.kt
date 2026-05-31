package com.example.proyecto_damian_compose.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_damian_compose.R
import com.example.proyecto_damian_compose.modelo.DatosDemo
import com.example.proyecto_damian_compose.modelo.Usuario

@PreviewScreenSizes
@Composable
fun PruebaGaleriaPreview() {
    PantallaGaleria(DatosDemo.usuarios[1], volver = {})
}

@Composable
fun PantallaGaleria(usuario: Usuario, volver: () -> Unit = {}) {

    var mostrarMenuAgregarPelicula by remember { mutableStateOf(false) } //por defecto no se muestra
    //El codigo para mostrar menu va al final
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val esLandscape = maxWidth > maxHeight
        val esDesktop = maxWidth > 1000.dp

        Scaffold(topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp)
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.flecha_volver),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { volver() }
                )
                Text(
                    stringResource(R.string.titulo_galeria),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }
        }) { innerPadding ->
            when {

                esDesktop -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurfaceVariant),
                                onClick = {
                                    mostrarMenuAgregarPelicula = true
                                }) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(R.string.icono_añadir_pelicula),
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            }
                        }

                        items(DatosDemo.peliculas) { pelicula ->
                            val guardada: Boolean = usuario.listaPeliculas.contains(pelicula)
                            Card(
                                modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
                                content = {
                                    Image(
                                        painter = painterResource(pelicula.imagen),
                                        contentDescription = stringResource(R.string.imagen_pelicula),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        alpha = if (guardada) 0.5f else 1f
                                    )
                                },
                                onClick = {
                                    if (!guardada) {
                                        usuario.listaPeliculas.add(pelicula)
                                        volver()
                                    }
                                })
                        }
                    }
                }

                esLandscape -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
                            Card(
                                modifier = Modifier
                                    .width(160.dp)
                                    .aspectRatio(2f / 3f)
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                onClick = { mostrarMenuAgregarPelicula = true }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(R.string.icono_añadir_pelicula),
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            }
                        }
                        items(DatosDemo.peliculas) { pelicula ->
                            val guardada = usuario.listaPeliculas.contains(pelicula)
                            Card(
                                modifier = Modifier
                                    .width(160.dp)
                                    .aspectRatio(2f / 3f)
                                    .padding(vertical = 4.dp),
                                onClick = {
                                    if (!guardada) {
                                        usuario.listaPeliculas.add(pelicula)
                                        volver()
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(pelicula.imagen),
                                    contentDescription = stringResource(R.string.imagen_pelicula),
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    alpha = if (guardada) 0.5f else 1f
                                )
                            }
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurfaceVariant),
                                onClick = {
                                    mostrarMenuAgregarPelicula = true
                                }) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(R.string.icono_añadir_pelicula),
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            }
                        }

                        items(DatosDemo.peliculas) { pelicula ->
                            val guardada: Boolean = usuario.listaPeliculas.contains(pelicula)
                            Card(
                                modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
                                content = {
                                    Image(
                                        painter = painterResource(pelicula.imagen),
                                        contentDescription = stringResource(R.string.imagen_pelicula),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        alpha = if (guardada) 0.5f else 1f
                                    )
                                },
                                onClick = {
                                    if (!guardada) {
                                        usuario.listaPeliculas.add(pelicula)
                                        volver()
                                    }
                                })
                        }
                    }
                }
            }

            if (mostrarMenuAgregarPelicula) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        AgregarPelicula(
                            usuario = usuario,
                            cerrarMenu = { mostrarMenuAgregarPelicula = false })
                    }
                }
            }
        }
    }
}