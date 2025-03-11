package com.example.proyectofacturas.navegacion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import com.example.proyectofacturas.vistas.PantallaEditarFactura
import com.example.proyectofacturas.vistas.PantallaLogin
import com.example.proyectofacturas.vistas.PantallaPerfil
import com.example.proyectofacturas.vistas.PantallaRegistro
import com.example.proyectofacturas.vistas.PantallaVideo

@Composable
fun NavigationWrapper() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val facturaViewModel: FacturaViewModel = viewModel() // Crear el ViewModel aquí

    // Accede a SharedPreferences para verificar si el usuario ha iniciado sesión antes
    val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)//Evita que otras apps puedan acceder a la informacion
    val haIniciadoSesionAntes = sharedPref.getBoolean("hasLoggedInBefore", false)

    // Define la pantalla inicial en función del valor de SharedPreferences
    val pantallaInicial = if (haIniciadoSesionAntes) {
        "pantallaAutenticacion" // Autenticación biométrica
    } else {
        "pantallaLogin" // Pantalla de login normal
    }

    NavHost(navController = navController, startDestination = pantallaInicial) {

        composable("pantallaLogin") { PantallaLogin(navController) }

        composable("pantallaRegistro") { PantallaRegistro(navController) } // Nueva pantalla de registro

        composable("pantallaAutenticacion") { PantallaAutenticacionBiometrica(navController) }

        composable("facturas") {
            PantallaFacturas(navController, facturaViewModel) // Pasar el ViewModel
        }

        composable("pantallaVideo") {
            PantallaVideo(navController)
        }

        composable("crear_factura") {
            PantallaCrearFactura(navController, facturaViewModel) // Pasar el ViewModel
        }

        composable("detalle_factura/{facturaId}") { backStackEntry ->
            val facturaId = backStackEntry.arguments?.getString("facturaId") ?: ""
            PantallaDetalleFactura(navController, facturaId, facturaViewModel) // Pasar el ID
        }

        composable("perfil") {
            PantallaPerfil(navController, facturaViewModel) // Pasar el ViewModel
        }

        composable("editar_factura/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            PantallaEditarFactura(navController, id, facturaViewModel)
        }
    }
}


