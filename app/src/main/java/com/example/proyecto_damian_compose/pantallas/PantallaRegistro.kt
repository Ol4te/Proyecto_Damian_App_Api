package com.example.proyecto_damian_compose.pantallas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proyecto_damian_compose.modelo.DatosDemo
import com.example.proyecto_damian_compose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.proyecto_damian_compose.api.UsuarioApi
import com.example.proyecto_damian_compose.controller.RetrofitConfig
import android.util.Log

// Aquí he jugado bastante porque quería añadir un icono gif al validar los datos el gif para -> Y volvemos a la pantalla inicial
@PreviewScreenSizes
@Composable
fun PreviewPantallaRegistro() {
    PantallaRegistro { string, string1 -> }
}

@Composable //LE pasamos variables a nuestra funcion
fun PantallaRegistro(
    cerrarRegistro: () -> Unit = {},
    enviarDatosLogin: (String, String) -> Unit = { _, _ -> }
) { //Lo conectamos con el icono de cerrar
    var animarEmote by remember { mutableStateOf(true) } //Vamos a usar un componente llamado AsynImage (linea 62)

    //Variables para los fields
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var emailTexto by remember { mutableStateOf("") }
    var passwordTexto by remember { mutableStateOf("") }

    var confirmarPasswordTexto by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    //variables para animar el boton de registro

    var botonPresionado by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) } //Para añadir un pequeño delay <- Asi simulamos el loading real y damos tiempo a la animacion a completarse

    var botonVisible by remember { mutableStateOf(true) }

    var scope = rememberCoroutineScope() // Es un timer de la aplicacion

    val animatedWidth by animateDpAsState(
        targetValue = if (botonPresionado) 80.dp else 200.dp, //Boton se encoje
        animationSpec = tween(durationMillis = 500)

    )

    //Variable de animacion


    //variables para errores
    var errorNombre by remember { mutableStateOf("") }
    var errorApellido by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf("") }
    var errorPassword by remember { mutableStateOf("") }
    var errorConfirmar by remember { mutableStateOf("") }

    val msgErrorNombre = stringResource(R.string.error_nombre_invalido)
    val msgErrorApellido = stringResource(R.string.error_apellido_invalido)
    val msgErrorEmail = stringResource(R.string.error_email_invalido)
    val msgErrorPassword = stringResource(R.string.error_password_corta)
    val msgErrorConfirmar = stringResource(R.string.error_passwords_no_coinciden)

    //Funcion para validaciones
    fun validar(): Boolean { //Validamos en el boton

        var esCorrecto = true

        //Se resetean los errores
        errorNombre = ""
        errorApellido = ""
        errorEmail = ""
        errorPassword = ""
        errorConfirmar = ""

        if (nombre.isBlank()) {
            esCorrecto = false
            errorNombre = msgErrorNombre
        }

        if (apellido.isBlank()) {
            esCorrecto = false
            errorApellido = msgErrorApellido
        }

        if (emailTexto.isBlank() || !emailTexto.contains("@") || emailTexto.isEmpty()) {
            errorEmail = msgErrorEmail
            esCorrecto = false
        }

        if (passwordTexto.length < 6) {
            errorPassword = msgErrorPassword
            esCorrecto = false
        }

        if (confirmarPasswordTexto != passwordTexto) {
            errorConfirmar = msgErrorConfirmar
            esCorrecto = false
        }
        //Si todo funciona devuelve true
        return esCorrecto;
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(10.dp))
        //Cerrar Registro
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.cerrar_registro),
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(30.dp)
                )
                .clickable {
                    cerrarRegistro()
                })

        Spacer(Modifier.height(40.dp))


            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = { Text(stringResource(R.string.campo_nombre)) },
                shape = RoundedCornerShape(20.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.icono_nombre),
                        Modifier.size(24.dp)
                    )
                },
                isError = errorNombre.isNotEmpty(),
                supportingText = { if (errorNombre.isNotEmpty()) Text(errorNombre) }
            ) //Si hay un error registrado por la tecla validar lo muestra,


            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                placeholder = { Text(stringResource(R.string.campo_apellido)) },
                shape = RoundedCornerShape(20.dp),
                isError = errorApellido.isNotEmpty(),
                supportingText = { Text(errorApellido) })





        OutlinedTextField(
            value = emailTexto,
            onValueChange = { emailTexto = it },
            placeholder = { Text(stringResource(R.string.campo_email_registro)) },
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.icono_email)
                )
            },
            singleLine = true,
            isError = errorEmail.isNotEmpty(),
            supportingText = {
                Text(errorEmail)
            })



        OutlinedTextField(
            value = passwordTexto,
            onValueChange = { passwordTexto = it },
            placeholder = { Text(stringResource(R.string.campo_contrasena)) },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.icono_contrasena),
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible })
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = errorPassword.isNotEmpty(),
            supportingText = { Text(errorPassword) })


        OutlinedTextField(
            value = confirmarPasswordTexto,
            onValueChange = { confirmarPasswordTexto = it },
            placeholder = { Text(stringResource(R.string.campo_repetir_password)) },
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.icono_contrasena),
                    Modifier
                        .size(24.dp)
                        .clickable { passwordVisible = !passwordVisible })
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            isError = errorConfirmar.isNotEmpty(),
            supportingText = { Text(errorConfirmar) }
        )

        Spacer(Modifier.height(10.dp))

        Row(Modifier.padding(horizontal = 30.dp), verticalAlignment = Alignment.CenterVertically) {
            //TODO Renderizador de GIF
            //No aparece en Preview solo en el Play
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(R.drawable.loading_gif)
                    .decoderFactory(coil.decode.GifDecoder.Factory()).build(),
                contentDescription = stringResource(R.string.loading_icon_desc),
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(Modifier.height(5.dp))



        AnimatedVisibility(visible = botonVisible, exit = fadeOut()) {
            Button(onClick = {

                //Hacemos trim y validamos

                if (validar()) {
                    nombre = nombre.trim()
                    apellido = apellido.trim()
                    emailTexto = emailTexto.trim()
                    passwordTexto = passwordTexto.trim()

//                    DatosDemo.registrar_Usuario(
//                        nombre,
//                        apellido,
//                        emailTexto,
//                        passwordTexto
//                    ) //Registramos el nuevo usuario <-
//                    enviarDatosLogin(emailTexto, passwordTexto) //Devolvemos los datos
//                    botonPresionado = true;
//                    loading = true;

//                    scope.launch {
//                        delay(400)
//                        botonVisible = false
//                    }

                    scope.launch {

                        try {

                            val usuario = UsuarioApi(
                                email = emailTexto,
                                password = passwordTexto
                            )

                            RetrofitConfig.userApiService().crearCuenta(usuario)

                            enviarDatosLogin(emailTexto, passwordTexto)

                            botonPresionado = true
                            loading = true

                            delay(400)
                            botonVisible = false

                        } catch (e: Exception) {
                            Log.e("REGISTRO", "Error", e)
                            errorEmail = e.message ?: "Error"
                        }
                    }
                }


            }, modifier = Modifier.width(animatedWidth)) {
                AnimatedVisibility(visible = !loading, exit = fadeOut()) {
                    Text(stringResource(R.string.boton_confirmar))
                }
            }
        }
    }
}