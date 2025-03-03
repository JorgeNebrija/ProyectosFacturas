package com.example.proyectofacturas.modelos

import kotlinx.serialization.Serializable

@Serializable
data class Proyecto(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val tecnologias: List<String> = emptyList(),
    val foto: String = "",
    val autor: String = "",
    val direccionAutor: String = "",
    val cifAutor: String = "",
    val cliente: String = "",
    val direccionCliente: String = "",
    val cifCliente: String = "",
    val codigo: String = ""
)
