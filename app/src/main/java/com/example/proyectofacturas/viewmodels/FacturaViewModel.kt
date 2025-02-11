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

    fun agregarFactura(nuevaFactura: Factura) {
        val nuevaFacturaId = db.collection("facturas").document().id // Genera un ID único
        db.collection("facturas")
            .document(nuevaFacturaId)
            .set(nuevaFactura)  // Usa set en lugar de add
            .addOnSuccessListener {
                cargarFacturas() // Recargar la lista después de añadir una nueva factura
                println("Factura agregada correctamente con ID: $nuevaFacturaId")
            }
            .addOnFailureListener { exception ->
                println("Error al agregar la factura: ${exception.message}")
            }
    }
}
