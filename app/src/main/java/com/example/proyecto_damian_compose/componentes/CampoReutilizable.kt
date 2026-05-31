package com.example.proyecto_damian_compose.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Usados en AgregarPelicula
@Composable
fun CampoReutilizable(
    valor: String,
    cambioValor: (String) -> Unit,
    placeholder: String,
    error: Boolean = false
){
    OutlinedTextField(
        value = valor,
        onValueChange = cambioValor,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(placeholder)
        },
        shape = RoundedCornerShape(20.dp),
        isError = error
    )
}