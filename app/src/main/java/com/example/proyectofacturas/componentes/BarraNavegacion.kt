package com.example.proyectofacturas.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    Surface (
        color = Color.White,  // Color blanco para el fondo del BottomAppBar
        modifier = Modifier.fillMaxWidth()
    ){
        BottomAppBar(
            containerColor = Color.White,
            modifier = Modifier.fillMaxWidth()
                .background(Color.White)
        ) {
            // Primer ícono (Factura)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaFacturas(navController) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_factura),
                    contentDescription = "Facturas",
                    tint = Color(0xFF8181A5), // Color de ícono
                    modifier = Modifier.size(34.dp)
                )
            }

            // Segundo ícono (Crear)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaCreacion(navController) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_crear),
                    contentDescription = "Crear Factura",
                    tint = Color(0xFF8181A5),
                    modifier = Modifier.size(34.dp) // Ícono más pequeño
                )
            }

            // Tercer ícono (Perfil)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaPerfil(navController) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Perfil",
                    tint = Color(0xFF8181A5),
                    modifier = Modifier.size(34.dp) // Ícono más pequeño
                )
            }
        }
    }
}
