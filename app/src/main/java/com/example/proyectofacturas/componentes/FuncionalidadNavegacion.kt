package com.example.proyectofacturas.componentes
import androidx.navigation.NavController

object FuncionalidadNavegacion {

    fun irAPantallaFacturas(navController: NavController) {
        navController.navigate("pantallaFacturas")
    }

    fun irAPantallaCreacion(navController: NavController) {
        navController.navigate("pantallaCrearFactura")
    }

    fun irAPantallaPerfil(navController: NavController) {
        navController.navigate("pantallaPerfil")
    }
}
