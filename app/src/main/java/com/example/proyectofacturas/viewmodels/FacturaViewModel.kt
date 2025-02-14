package com.example.proyectofacturas.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.example.proyectofacturas.modelos.Factura

class FacturaViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _facturas = MutableLiveData<List<Factura>>()
    val facturas: LiveData<List<Factura>> = _facturas

    init {
        cargarFacturas()
    }

    /**
     * Cargar todas las facturas desde Firestore
     */
    fun cargarFacturas() {
        db.collection("facturas")
            .get()
            .addOnSuccessListener { result ->
                val listaFacturas = result.documents.mapNotNull { document ->
                    document.toObject<Factura>()?.copy(id = document.id) // ✅ Corrección en la asignación de ID
                }
                _facturas.value = listaFacturas
            }
            .addOnFailureListener { exception ->
                Log.e("FacturaViewModel", "Error al cargar facturas", exception)
            }
    }

    /**
     * Agregar una nueva factura a Firestore
     */
    fun agregarFactura(nuevaFactura: Factura) {
        db.collection("facturas")
            .add(nuevaFactura) // ✅ Firestore generará el ID automáticamente
            .addOnSuccessListener { documentReference ->
                Log.d("FacturaViewModel", "Factura agregada con ID: ${documentReference.id}")
                cargarFacturas() // Recargar lista después de añadir una nueva factura
            }
            .addOnFailureListener { exception ->
                Log.e("FacturaViewModel", "Error al agregar factura", exception)
            }
    }

    /**
     * Obtener una factura específica por su ID
     */
    fun obtenerFacturaPorId(id: String): LiveData<Factura?> {
        val facturaActual = MutableLiveData<Factura?>()

        db.collection("facturas")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    facturaActual.value = document.toObject<Factura>()?.copy(id = document.id)
                } else {
                    facturaActual.value = null
                    Log.e("FacturaViewModel", "No se encontró la factura con ID: $id")
                }
            }
            .addOnFailureListener { exception ->
                facturaActual.value = null
                Log.e("FacturaViewModel", "Error al obtener la factura", exception)
            }

        return facturaActual
    }
}
