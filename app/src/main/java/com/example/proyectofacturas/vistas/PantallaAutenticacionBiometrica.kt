package com.example.proyectofacturas.vistas

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.componentes.AutenticacionBiometrica

@Composable
fun PantallaAutenticacionBiometrica(navController: NavController) {
    val contexto = LocalContext.current
    var mensajeAutenticacion by remember { mutableStateOf("") }

    // Instancia de la autenticación biométrica
    val autenticacionBiometrica = AutenticacionBiometrica(contexto) {
        mensajeAutenticacion = "Autenticación Exitosa"
        navController.navigate("facturas") // Redirige a la pantalla de facturas
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Autenticación Requerida", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { autenticacionBiometrica.iniciarAutenticacion() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Autenticarse con Huella Digital", color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(mensajeAutenticacion, style = MaterialTheme.typography.bodyMedium)
    }
}
