package com.example.proyectofacturas.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofacturas.vistas.PantallaCrearFactura
import com.example.proyectofacturas.vistas.PantallaDetalleFactura
import com.example.proyectofacturas.vistas.PantallaFacturas
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import com.example.proyectofacturas.vistas.PantallaAutenticacionBiometrica
import com.example.proyectofacturas.vistas.PantallaLogin
import com.example.proyectofacturas.vistas.PantallaRegistro

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val facturaViewModel: FacturaViewModel = viewModel() // Crear el ViewModel aquí

    NavHost(navController = navController, startDestination = "pantallaAutenticacion") {

        composable("pantallaLogin") { PantallaLogin(navController) }

        composable("pantallaRegistro") { PantallaRegistro(navController) } // Nueva pantalla de registro

        composable("pantallaAutenticacion") { PantallaAutenticacionBiometrica(navController) }


        composable("facturas") {
            PantallaFacturas(navController, facturaViewModel) // Pasar el ViewModel
        }
        composable("crear_factura") {
            PantallaCrearFactura(navController, facturaViewModel) // Pasar el ViewModel
        }
        composable("detalle_factura/{facturaId}") { backStackEntry ->
            val facturaId = backStackEntry.arguments?.getString("facturaId") ?: ""
            PantallaDetalleFactura(navController, facturaId, facturaViewModel) // Pasar el ID
        }


    }
}

