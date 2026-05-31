package com.example.proyecto_damian_compose.ui.theme

import android.app.Activity
import android.hardware.lights.Light
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = moradoPrincipal,
    onPrimary = blanco,

    background = Color(0xff1e1e1e),
    onBackground = blanco,

    surface = Color(0xff2a2a2a),
    onSurface = blanco,

    outline = Color(0xff444444),
    onSurfaceVariant = Color(0xffaaaaaa)
)

private val LightColorScheme = lightColorScheme(

    primary = Purple40,
    onPrimary = Color.White,

    background = Color.White,
    onBackground = Color.Black,

    surface = Color(0xfff5f5f5),
    onSurface = Color.Black,

    outline = Color(0xFFCCCCCC),
    onSurfaceVariant = Color(0xFF666666)



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Proyecto_Damian_ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme //Cambio este linea para desactivar el dynamic y poder usar los dos modos


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}