package com.example.proyectofacturas.modelos

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Factura(
    @Exclude var id: String? = null,  // ID autogenerado por Firebase
    val fecha: String = "",
    val numeroFactura: String = "",
    val tipo: String = "emitida",  // Puede ser "emitida" o "recibida"
    val nombre: String = "",
    val cif: String = "",
    val baseImponible: Double = 0.0,
    val iva: Double = 0.0,
    val irpf: Double = 0.0,
    val total: Double = 0.0
) : Serializable
