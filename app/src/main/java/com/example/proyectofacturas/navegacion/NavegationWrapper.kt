package com.example.proyectofacturas.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofacturas.vistas.PantallaCrearFactura
import com.example.proyectofacturas.vistas.PantallaFacturas

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "facturas") {
        composable("facturas") { PantallaFacturas(navController) }
        composable("crear_factura") { PantallaCrearFactura(navController) }
        composable("detalle_factura/{facturaId}") { backStackEntry ->
            val facturaId = backStackEntry.arguments?.getString("facturaId")
            facturaId?.let { PantallaDetalleFactura(navController, it) }
        }
    }
}
