package com.example.proyectofacturas.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectofacturas.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        NavigationItem(
            icon = R.drawable.ic_factura,
            label = "Facturas",
            onClick = { FuncionalidadNavegacion.irAPantallaFacturas(navController) }
        )
        NavigationItem(
            icon = R.drawable.ic_crear,
            label = "Crear",
            onClick = { FuncionalidadNavegacion.irAPantallaCreacion(navController) }
        )
        NavigationItem(
            icon = R.drawable.ic_perfil,
            label = "Perfil",
            onClick = { FuncionalidadNavegacion.irAPantallaPerfil(navController) }
        )
    }
}

@Composable
fun NavigationItem(icon: Int, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label
        )
        Text(text = label)
    }
}
