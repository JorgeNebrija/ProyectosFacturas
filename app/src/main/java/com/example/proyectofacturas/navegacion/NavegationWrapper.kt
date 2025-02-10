package com.example.proyectofacturas.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofacturas.vistas.PantallaCrearFactura
import com.example.proyectofacturas.vistas.PantallaDetalleFactura
import com.example.proyectofacturas.vistas.PantallaFacturas
import com.example.proyectofacturas.viewmodels.FacturaViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val facturaViewModel: FacturaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "facturas") {
        composable("facturas") { PantallaFacturas(navController, facturaViewModel) }
        composable("crear_factura") { PantallaCrearFactura(navController) }
        composable("detalle_factura/{facturaId}") { backStackEntry ->
            val facturaId = backStackEntry.arguments?.getString("facturaId")
            facturaId?.let { PantallaDetalleFactura(navController, it, facturaViewModel) }
        }
    }
}
