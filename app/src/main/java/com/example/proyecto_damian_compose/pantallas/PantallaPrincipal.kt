package com.example.proyecto_damian_compose.pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_damian_compose.modelo.DatosDemo

import com.example.proyecto_damian_compose.modelo.Pelicula
import com.example.proyecto_damian_compose.modelo.Usuario
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import coil.compose.AsyncImage
import com.example.proyecto_damian_compose.R
import com.example.proyecto_damian_compose.api.PeliculaApi
import com.example.proyecto_damian_compose.api.Session
import com.example.proyecto_damian_compose.controller.RetrofitConfig


//ESta pantalla la hago con scaffol




@Composable
fun PantallaPrincipal(abrirAgregarPelicula: () -> Unit = {}) {

    var peliculas by remember {
        mutableStateOf<List<PeliculaApi>>(emptyList())
    }

    LaunchedEffect(Unit) {
        try {
            Log.d("TOKEN", Session.token)
            peliculas =
                RetrofitConfig.peliculaApiService()
                    .obtenerTodas(Session.token)

        } catch (e: Exception) {
            Log.e("PELICULAS", "ERROR", e)
        }
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.titulo_mis_peliculas),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }, floatingActionButton = { //TODO
        FloatingActionButton(
            onClick = {  abrirAgregarPelicula()},
            containerColor = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(25.dp)
        ) { Icon(Icons.Default.Add, contentDescription = stringResource(R.string.icono_añadir_pelicula), Modifier.size(30.dp)) }
    }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            //Para cada objeto de la lista se llama a la funcion
            //Usamos SwipetoDismiss para borrar
            items(peliculas) { pelicula -> //Generamos una accion y una box
        
                var accionBorrar = rememberSwipeToDismissBoxState()

                if (
                    accionBorrar.currentValue == SwipeToDismissBoxValue.EndToStart ||
                    accionBorrar.currentValue == SwipeToDismissBoxValue.StartToEnd
                ) {

                    LaunchedEffect(pelicula.id) {

                        pelicula.id?.let { id ->

                            RetrofitConfig.peliculaApiService()
                                .borrarPelicula(
                                    Session.token,
                                    id
                                )

                            peliculas = peliculas.filter {
                                it.id != id
                            }
                        }
                    }
                }

                SwipeToDismissBox(
                    state = accionBorrar,
                    backgroundContent = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                ) {
                    MostrarPelicula(pelicula)
                }



            }
        }
    }
}



@Composable
fun MostrarPelicula(pelicula: PeliculaApi) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = {}) {
        Row(Modifier.padding(10.dp)) {
            AsyncImage(
                model = pelicula.imagen,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(130.dp)
            )
            //Datos pelicula
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(10.dp)
            ) {
                Text(
                    pelicula.titulo,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row() {
                    Column(Modifier.weight(1f)) {
                        Spacer(Modifier.height(15.dp))
                        Text(stringResource(R.string.label_genero)+ pelicula.genero)
                        Spacer(Modifier.height(10.dp))
                        Text(stringResource(R.string.label_director)+pelicula.director)
                    }
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "??",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 35.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }


        }
    }
}





@Composable
fun ColorPuntuacion(puntuacion: Double): Color {
    return when{
        puntuacion >= 8 -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
}
/*
@Composable
fun colorRating(puntuacion: Double): Color{
    if(puntuacion >= 7) return MaterialTheme.colorScheme.primary
    else  return MaterialTheme.colorScheme.onSurfaceVariant

}*/
