package com.example.proyectofacturas.modelos

import com.google.firebase.firestore.Exclude
import kotlinx.serialization.Serializable


@Serializable
data class Factura(
    var id: String? = null,  // ✅ Hacerlo nullable para evitar errores
    val numeroFactura: String = "",
    val fecha: String = "",
    val nombre: String = "",
    val direccion: String = "",
    val cliente: String = "",
    val direccionCliente: String = "",
    val cif: String = "",

    val cifCliente: String = "",
    val baseImponible: Double = 0.0,
    val iva: Double = 0.0,
    val irpf: Double = 0.0,
    val total: Double = 0.0,
    val tipo: String = ""
)

sealed class TipoFactura {
    object Compra : TipoFactura()
    object Venta : TipoFactura()
}



