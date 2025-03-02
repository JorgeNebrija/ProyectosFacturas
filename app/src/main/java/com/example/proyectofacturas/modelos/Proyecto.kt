package com.example.proyectofacturas.modelos

import kotlinx.serialization.Serializable

@Serializable
data class Proyecto(
    val nombre: String,
    val codigo: String
)
