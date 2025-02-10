package com.example.proyectofacturas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


// Importar la tipografía desde Type.kt

private val ColoresOscuros = darkColorScheme(
    primary = AzulPrincipal,
    secondary = VerdePagado,
    background = NegroSuave,
    surface = AzulFondo
)

private val ColoresClaros = lightColorScheme(
    primary = AzulPrincipal,
    secondary = VerdePagado,
    background = AzulFondo,
    surface = Blanco
)

@Composable
fun TemaFacturaApp(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colores = if (darkTheme) ColoresOscuros else ColoresClaros

    MaterialTheme(
        colorScheme = colores,
        typography = TipografiaFactura,
        content = content
    )
}
