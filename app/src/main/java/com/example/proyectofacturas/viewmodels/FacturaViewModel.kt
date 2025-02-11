package com.example.proyectofacturas.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofacturas.modelos.Factura
import com.google.firebase.firestore.FirebaseFirestore

class FacturaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Lista de facturas en LiveData
    private val _facturas = MutableLiveData<List<Factura>>(emptyList())
    val facturas: LiveData<List<Factura>> get() = _facturas

    init {
        cargarFacturas()
    }

    // Método para cargar facturas desde Firebase
    fun cargarFacturas() {
        db.collection("facturas")
            .get()
            .addOnSuccessListener { result ->
                val listaFacturas = result.mapNotNull { document ->
                    document.toObject(Factura::class.java).apply {
                        id = document.id
                    }
                }
                _facturas.value = listaFacturas
            }
            .addOnFailureListener {
                // Manejo de errores (opcional, puedes mostrar un mensaje)
            }
    }

    // Método para agregar una nueva factura a Firebase
    fun agregarFactura(nuevaFactura: Factura) {
        db.collection("facturas")
            .add(nuevaFactura)
            .addOnSuccessListener {
                cargarFacturas() // Recargar las facturas después de añadir una nueva
            }
            .addOnFailureListener {
                // Manejo de errores (opcional)
            }
    }
}
