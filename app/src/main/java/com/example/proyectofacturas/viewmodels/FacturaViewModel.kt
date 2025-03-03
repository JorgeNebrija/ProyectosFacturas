package com.example.proyectofacturas.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.modelos.Proyecto

class FacturaViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _facturas = MutableLiveData<List<Factura>>()
    val facturas: LiveData<List<Factura>> = _facturas

    private val _proyectos = MutableLiveData<List<Proyecto>>()
    val proyectos: LiveData<List<Proyecto>> = _proyectos

    init {
        cargarFacturas()
        cargarProyectos()
    }

    /**
     * Cargar todas las facturas desde Firestore
     */
    fun cargarFacturas() {
        db.collection("facturas")
            .get()
            .addOnSuccessListener { result ->
                val listaFacturas = result.documents.mapNotNull { document ->
                    document.toObject<Factura>()?.copy(id = document.id)
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
            .add(nuevaFactura)
            .addOnSuccessListener { documentReference ->
                Log.d("FacturaViewModel", "Factura agregada con ID: ${documentReference.id}")
                cargarFacturas()
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

    /**
     * Actualizar una factura existente en Firestore
     */
    fun actualizarFactura(id: String, facturaActualizada: Factura) {
        db.collection("facturas").document(id)
            .set(facturaActualizada)
            .addOnSuccessListener {
                Log.d("FacturaViewModel", "Factura actualizada con ID: $id")
                cargarFacturas() // Recargar la lista tras actualizar
            }
            .addOnFailureListener { exception ->
                Log.e("FacturaViewModel", "Error al actualizar la factura con ID: $id", exception)
            }
    }

    /**
     * Eliminar una factura por su ID
     */
    fun eliminarFactura(id: String) {
        db.collection("facturas").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("FacturaViewModel", "Factura eliminada correctamente con ID: $id")
                cargarFacturas()
            }
            .addOnFailureListener { exception ->
                Log.e("FacturaViewModel", "Error al eliminar la factura con ID: $id", exception)
            }
    }

    fun cargarProyectos() {
        db.collection("proyectos")
            .get()
            .addOnSuccessListener { result ->
                val listaProyectos = result.documents.mapNotNull { document ->
                    document.toObject<Proyecto>()?.copy(id = document.id)
                }
                _proyectos.value = listaProyectos
            }
            .addOnFailureListener { exception ->
                Log.e("FacturaViewModel", "Error al obtener proyectos", exception)
            }
    }



}
