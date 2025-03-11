package com.example.proyectofacturas.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectofacturas.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Surface(
        color = Color.White, // Fondo blanco
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp) // Un poco más alto
    ) {
        BottomAppBar(
            containerColor = Color.White, // Fondo blanco
            modifier = Modifier.fillMaxWidth()
        ) {
            // Primer ícono (Factura)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaFacturas(navController) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp) // Aumentado un poco
                        .background(Color(0xFFE9F0FF), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_factura),
                        contentDescription = "Facturas",
                        tint = if (currentRoute == "facturas") Color(0xFF5E81F4) else Color(0xFF8181A5),
                        modifier = Modifier.size(26.dp) // Icono un poco más grande
                    )
                }
            }

            // Segundo ícono (Crear)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaCreacion(navController) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp) // Aumentado un poco
                        .background(Color(0xFFE9F0FF), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_crear),
                        contentDescription = "Crear Factura",
                        tint = if (currentRoute == "crear_factura") Color(0xFF5E81F4) else Color(0xFF8181A5),
                        modifier = Modifier.size(26.dp) // Icono un poco más grande
                    )
                }
            }

            // Tercer ícono (Perfil)
            IconButton(
                onClick = { FuncionalidadNavegacion.irAPantallaPerfil(navController) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp) // Aumentado un poco
                        .background(Color(0xFFE9F0FF), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_perfil),
                        contentDescription = "Perfil",
                        tint = if (currentRoute == "perfil") Color(0xFF5E81F4) else Color(0xFF8181A5),
                        modifier = Modifier.size(26.dp) // Icono un poco más grande
                    )
                }
            }
        }
    }
}
