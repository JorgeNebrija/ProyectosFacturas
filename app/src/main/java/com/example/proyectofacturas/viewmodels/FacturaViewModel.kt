package com.example.proyectofacturas.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofacturas.modelos.Factura
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FacturaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // LiveData para observar la lista de facturas
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
                    document.toObject<Factura>()?.apply {
                        id = document.id  // Asigna el id generado por Firebase
                    }
                }
                _facturas.value = listaFacturas
            }
            .addOnFailureListener { exception ->
                // Manejo de errores
                println("Error al cargar las facturas: ${exception.message}")
            }
    }

    // Método para agregar una nueva factura a Firebase
    fun agregarFactura(nuevaFactura: Factura) {
        db.collection("facturas")
            .add(nuevaFactura.copy(id = null)) // Excluye el campo id para autogenerarlo
            .addOnSuccessListener {
                cargarFacturas() // Recargar la lista después de añadir una nueva factura
            }
            .addOnFailureListener { exception ->
                println("Error al agregar la factura: ${exception.message}")
            }
    }
}
