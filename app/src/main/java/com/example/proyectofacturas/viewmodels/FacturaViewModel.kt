package com.example.proyectofacturas.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofacturas.modelos.Factura

class FacturaViewModel : ViewModel() {

    // Lista de facturas simulada (sin conexión a Firebase aún)
    private val _facturas = MutableLiveData<List<Factura>>(emptyList())
    val facturas: LiveData<List<Factura>> get() = _facturas

    // Método para cargar facturas (simulación por ahora)
    fun cargarFacturas() {
        val listaEjemplo = listOf(
            Factura("1", "2025-02-10", "001", "emitida", "Cliente 1", "B12345678", 100.0, 21.0, 0.0, 121.0),
            Factura("2", "2025-02-08", "002", "recibida", "Proveedor A", "C98765432", 200.0, 42.0, 0.0, 242.0)
        )
        _facturas.value = listaEjemplo
    }
}
