package com.example.proyectofacturas.modelos

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Factura(
    @Exclude var id: String? = null,  // ID autogenerado por Firebase
    val numeroFactura: String = "",
    val fecha: String = "",
    val nombre: String = "",          // Nombre de la empresa o proveedor
    val direccion: String = "",
    val cliente: String = "",
    val direccionCliente: String = "",
    val cif: String = "",
    val cifCliente: String = "",
    val baseImponible: Double = 0.0,
    val iva: Double = 0.0,
    val irpf: Double = 0.0,
    val total: Double = 0.0
) : Serializable
