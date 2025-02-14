package com.example.proyectofacturas.componentes
import androidx.navigation.NavController

object FuncionalidadNavegacion {

    fun irAPantallaFacturas(navController: NavController) {
        navController.navigate("facturas")
    }

    fun irAPantallaCreacion(navController: NavController) {
        navController.navigate("crear_factura")
    }

    fun irAPantallaPerfil(navController: NavController) {
        navController.navigate("perfil")
    }
}
