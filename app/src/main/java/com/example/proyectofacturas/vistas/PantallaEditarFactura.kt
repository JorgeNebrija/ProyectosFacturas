package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarFactura(
    navController: NavController,
    idFactura: String,
    viewModel: FacturaViewModel
) {
    val factura by viewModel.obtenerFacturaPorId(idFactura).observeAsState()

    if (factura != null) {
        val facturaData = factura!!

        var numeroFactura by remember { mutableStateOf(facturaData.numeroFactura) }
        var fecha by remember { mutableStateOf(facturaData.fecha) }
        var nombre by remember { mutableStateOf(facturaData.nombre) }
        var cliente by remember { mutableStateOf(facturaData.cliente) }
        var direccionEmisor by remember { mutableStateOf(facturaData.direccion) }
        var direccionCliente by remember { mutableStateOf(facturaData.direccionCliente) }
        var cifEmisor by remember { mutableStateOf(facturaData.cif) }
        var cifCliente by remember { mutableStateOf(facturaData.cifCliente) }
        var baseImponible by remember { mutableStateOf(facturaData.baseImponible.toString()) }
        var iva by remember { mutableStateOf(facturaData.iva.toString()) }
        var irpf by remember { mutableStateOf(facturaData.irpf.toString()) }
        var total by remember { mutableStateOf(facturaData.total.toString()) }
        var tipoFactura by remember { mutableStateOf(facturaData.tipo) }

        Scaffold(
            topBar = { Header(navController) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Text("Editar Factura", style = MaterialTheme.typography.headlineMedium) }
                item { InputDeDatos(value = numeroFactura, onValueChange = { numeroFactura = it }, label = "Número de Factura") }
                item { InputDeDatos(value = fecha, onValueChange = { fecha = it }, label = "Fecha", enabled = false) }
                item { InputDeDatos(value = nombre, onValueChange = { nombre = it }, label = "Nombre Empresa") }
                item { InputDeDatos(value = cliente, onValueChange = { cliente = it }, label = "Cliente") }
                item { InputDeDatos(value = direccionEmisor, onValueChange = { direccionEmisor = it }, label = "Dirección Emisor") }
                item { InputDeDatos(value = direccionCliente, onValueChange = { direccionCliente = it }, label = "Dirección Cliente") }
                item { InputDeDatos(value = cifEmisor, onValueChange = { cifEmisor = it }, label = "CIF Emisor") }
                item { InputDeDatos(value = cifCliente, onValueChange = { cifCliente = it }, label = "CIF Cliente") }
                item {
                    Button(
                        onClick = {
                            factura?.id?.let { facturaId ->
                                val facturaActualizada = Factura(
                                    id = facturaId, // ✅ Usamos el ID correcto de la factura existente
                                    numeroFactura = numeroFactura,
                                    fecha = fecha,
                                    nombre = nombre,
                                    direccion = direccionEmisor,
                                    cliente = cliente,
                                    direccionCliente = direccionCliente,
                                    cif = cifEmisor,
                                    cifCliente = cifCliente,
                                    baseImponible = baseImponible.toDoubleOrNull() ?: 0.0,
                                    iva = iva.toDoubleOrNull() ?: 0.0,
                                    irpf = irpf.toDoubleOrNull() ?: 0.0,
                                    total = total.toDoubleOrNull() ?: 0.0,
                                    tipo = tipoFactura
                                )
                                viewModel.actualizarFactura(facturaId, facturaActualizada) // ✅ Ahora usa un ID válido
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar cambios", color = Color.White)
                    }
                }
            }
        }
    } else {
        Text("Cargando factura...", color = Color.Gray)
    }
}
