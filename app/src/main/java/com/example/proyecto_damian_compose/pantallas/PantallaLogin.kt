package com.example.proyecto_damian_compose.pantallas


import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_damian_compose.R
import com.example.proyecto_damian_compose.api.Session
import com.example.proyecto_damian_compose.api.UsuarioApi
import com.example.proyecto_damian_compose.controller.RetrofitConfig
import com.example.proyecto_damian_compose.modelo.DatosDemo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@PreviewScreenSizes
@Composable
fun PantallaLoginPreview() {
    PantallaLogin()
}

@Composable
fun PantallaLogin(loginCorrecto: (String) -> Unit = {}) {

    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorLogin by remember { mutableStateOf(false) }
    var presionado by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    var mostrarRegistro by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val contexto = LocalContext.current //Para Cerrar App

    val animatedWidth by animateDpAsState(
        targetValue = if (presionado) 80.dp else 200.dp, animationSpec = tween(durationMillis = 500)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val esDesktop = maxWidth > 840.dp
            val esLandScape = maxHeight < 500.dp
            val imageWeight = if (esDesktop) 0.4f else 0.3f
            val spacerHeight = if (esDesktop) 60.dp else 30.dp

            val titleSize = if (esDesktop) 50.sp else 35.sp
            val subtitleSize = if (esDesktop) 35.sp else 25.sp
            val titleSpacerWeight = if (esDesktop) 6.dp else 4.dp
            val mostrarTextoImagen = !esLandScape || esDesktop

            //El Scroll me ha dado trabajo. Balancear las vistas en diferentes equipos y permitir el scroll me llevo a optar por solo habilitar el scroll en landscape para Login y Register
            val usarScroll = esLandScape
            val scrollState = rememberScrollState()


            Column(
                modifier = Modifier.fillMaxSize().then(if(usarScroll) Modifier.verticalScroll(scrollState)
                else Modifier),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen superior
                if (!esLandScape) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(imageWeight)
                    ) {
                        Image(
                            painterResource(id = R.drawable.login_superior),
                            contentDescription = stringResource(R.string.imagen_login_superior),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f))
                        )
                        Column {
                            Spacer(modifier = Modifier.height(35.dp))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(spacerHeight)
                            )
                            if (mostrarTextoImagen) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(
                                        stringResource(R.string.login_titulo),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = titleSize,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(
                                            modifier = Modifier
                                                .width(75.dp)
                                                .height(titleSpacerWeight)
                                                .background(color = MaterialTheme.colorScheme.background)
                                        )
                                        Text(
                                            stringResource(R.string.login_to),
                                            fontSize = subtitleSize,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.background
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(75.dp)
                                                .height(titleSpacerWeight)
                                                .background(color = MaterialTheme.colorScheme.background)
                                        )
                                    }
                                    Text(
                                        stringResource(R.string.login_your_account),
                                        color = MaterialTheme.colorScheme.background,
                                        fontSize = titleSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }
                    }
                }
                //Icono Cerrar / Close
                Row(
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.icono_cerrar_app),
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clickable { (contexto as? Activity)?.finish() })
                }
                // Parte inferior
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 16.dp).then(
                            if(!usarScroll) Modifier.weight(0.6f) else Modifier.fillMaxWidth()
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.width(300.dp),
                            placeholder = { Text(stringResource(R.string.campo_email)) },
                            value = emailField,
                            onValueChange = { emailField = it },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(R.string.icono_email)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            isError = errorLogin,
                            supportingText = { if (errorLogin) Text(stringResource(R.string.error_datos_incorrectos)) })
                        OutlinedTextField(
                            modifier = Modifier.width(300.dp),
                            value = passwordField,
                            placeholder = { Text(stringResource(R.string.campo_contrasena)) },
                            onValueChange = { passwordField = it },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = if (passwordVisible) R.drawable.visibility else R.drawable.no_visibility),
                                    contentDescription = stringResource(R.string.icono_contrasena),
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable { passwordVisible = !passwordVisible })
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            isError = errorLogin,
                            supportingText = { if (errorLogin) Text(stringResource(R.string.error_datos_incorrectos)) })
                        AnimatedVisibility(visible = visible, exit = fadeOut()) {
                            Button(onClick = {
                                emailField = emailField.trim()
                                passwordField = passwordField.trim()
//                                if (DatosDemo.comprobarLogin(emailField, passwordField)) {
//                                    presionado = true
//                                    loading = true
//                                    scope.launch { delay(300); visible = false }
//                                    loginCorrecto(emailField)
//                                } else {
//                                    errorLogin = true
//                                }
                                //TODO
                                scope.launch {

                                    try {

                                        val usuario = UsuarioApi(
                                            email = emailField,
                                            password = passwordField
                                        )

                                        val respuesta =
                                            RetrofitConfig.userApiService().login(usuario)

                                        Log.d("LOGIN", respuesta.toString())

                                        val token =
                                            respuesta["token"]

                                        if (token != null) {

                                            Session.token = "Bearer $token"

                                            val peliculas =
                                                RetrofitConfig.peliculaApiService()
                                                    .obtenerTodas(Session.token)
                                            Log.d("PELICULAS", peliculas.toString())

                                            loginCorrecto(emailField)



                                        }

                                    } catch (e: Exception) {
                                        Log.e("PELICULAS", "ERROR", e)
                                        errorLogin = true

                                    }

                                }
                            }, modifier = Modifier.width(animatedWidth)) {
                                AnimatedVisibility(visible = !loading, exit = fadeOut()) {
                                    Text(stringResource(R.string.login_titulo))
                                }
                            }
                        }
                    }

                    // Parte de registro e iconos
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp)
                        ) {
                            Text(stringResource(R.string.texto_registrarse), fontSize = 16.sp)
                            Text(
                                stringResource(R.string.boton_registrarse),
                                modifier = Modifier
                                    .padding(7.dp)
                                    .clickable { mostrarRegistro = true },
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(MaterialTheme.colorScheme.outlineVariant)
                                    .height(2.dp)
                            )
                            Spacer(Modifier.width(30.dp))
                            Spacer(
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(MaterialTheme.colorScheme.outlineVariant)
                                    .height(2.dp)
                            )
                        }
                        Row(
                            Modifier.padding(horizontal = 50.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = stringResource(R.string.acceso_rapido),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        emailField = "damian@gmail.com"; passwordField = "123456"
                                    })
                            Image(
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = stringResource(R.string.acceso_rapido),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        emailField = "miguel@gmail.com"; passwordField = "123456"
                                    })
                            Image(
                                painter = painterResource(id = R.drawable.apple),
                                contentDescription = stringResource(R.string.acceso_rapido),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        emailField = "laura@gmail.com"; passwordField = "123456"
                                    })
                        }
                    }
                }
            }

            // Registro overlay
            AnimatedVisibility(visible = mostrarRegistro) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.verticalScroll(scrollState).fillMaxWidth()){
                        PantallaRegistro(
                            cerrarRegistro = { mostrarRegistro = false },
                            enviarDatosLogin = { email, contrasena ->
                                emailField = email; passwordField = contrasena; mostrarRegistro = false
                            })
                    }
                }
            }
        }
    }
}
