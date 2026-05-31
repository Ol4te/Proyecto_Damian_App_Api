package com.example.proyecto_damian_compose.pantallas

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.proyecto_damian_compose.R

//ESta pantalla la hago con scaffol


@PreviewScreenSizes
@Composable
fun PantallaPrincipalPreview(){
    PantallaPrincipal(DatosDemo.usuarios[3])
}
//Añadi SwipetoDismiss del Material 3 para hacer un poco mas entretenido lo de borrar peliculas

@PreviewScreenSizes
@Composable
fun MostrarPeliculaPreview(){
    MostrarPelicula(pelicula= DatosDemo.peliculas[1])
}

@Composable
fun PantallaPrincipal(usuario: Usuario, abrirGaleria: () -> Unit = {}) {

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
            onClick = { abrirGaleria() },
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
            items(usuario.listaPeliculas, key={it.id}) { pelicula -> //Generamos una accion y una box
        
                var accionBorrar = rememberSwipeToDismissBoxState()

                if(accionBorrar.currentValue == SwipeToDismissBoxValue.EndToStart || accionBorrar.currentValue ==
                    SwipeToDismissBoxValue.StartToEnd){
                    usuario.listaPeliculas.remove(pelicula)
                }

                    SwipeToDismissBox(state = accionBorrar, enableDismissFromEndToStart = true, enableDismissFromStartToEnd = true,
                        backgroundContent = {
                            val colorFondo =
                                when (accionBorrar.dismissDirection){
                                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error
                                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.background.copy(alpha = 0f)
                                }
                            val alignment = when(accionBorrar.dismissDirection){
                                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterStart
                                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterEnd
                                else -> Alignment.Center
                            }

                            Box(modifier = Modifier.fillMaxSize().background(colorFondo, shape = RoundedCornerShape(20.dp)).padding(horizontal = 20.dp),
                                contentAlignment = alignment){
                                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.icono_borrar), modifier = Modifier.size(60.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary)
                            }
                        })
                 {
                    MostrarPelicula(pelicula = pelicula)
                }
            }
        }
    }
}





@Composable
fun MostrarPelicula(pelicula: Pelicula) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = {}) {
        Row(Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(pelicula.imagen),
                contentDescription = stringResource(R.string.imagen_pelicula_desc),
                modifier = Modifier
                    .width(80.dp)
                    .height(130.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            //Datos pelicula
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(10.dp)
            ) {
                Text(
                    pelicula.titulo,
                    color = ColorPuntuacion(pelicula.puntuacion),
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
                            pelicula.puntuacion.toString(),
                            fontWeight = FontWeight.Bold,
                            color = ColorPuntuacion(pelicula.puntuacion),
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
