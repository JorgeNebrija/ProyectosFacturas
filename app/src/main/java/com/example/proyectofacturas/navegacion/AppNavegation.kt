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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val facturaViewModel: FacturaViewModel = viewModel()

    // Obtener el estado de la sesión desde SharedPreferences
    val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    val hasLoggedInBefore = sharedPref.getBoolean("hasLoggedInBefore", false)

    // Verificar si el usuario está autenticado en FirebaseAuth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Lógica para definir la pantalla de inicio:
    // - Si el usuario ha iniciado sesión previamente y sigue autenticado, ir a biometría.
    // - Si no ha iniciado sesión antes, ir a pantallaLogin.
    val startDestination = if (hasLoggedInBefore && currentUser != null) {
        "pantallaAutenticacion"
    } else {
        "pantallaLogin"
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("pantallaLogin") { PantallaLogin(navController) }

        composable("pantallaRegistro") { PantallaRegistro(navController) }

        composable("pantallaAutenticacion") { PantallaAutenticacionBiometrica(navController) }

        composable("facturas") { PantallaFacturas(navController, facturaViewModel) }

        composable("crear_factura") { PantallaCrearFactura(navController, facturaViewModel) }

        composable("detalle_factura/{facturaId}") { backStackEntry ->
            val facturaId = backStackEntry.arguments?.getString("facturaId") ?: ""
            PantallaDetalleFactura(navController, facturaId, facturaViewModel)
        }

        composable("perfil") { PantallaPerfil(navController, facturaViewModel) }

        composable("editar_factura/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            PantallaEditarFactura(navController, id, facturaViewModel)
        }

        composable("pantallaVideo") { PantallaVideo(navController) }
    }
}
