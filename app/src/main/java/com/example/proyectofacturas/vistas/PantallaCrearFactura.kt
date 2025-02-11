package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.OutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearFactura(navController: NavController, viewModel: FacturaViewModel) {
    var numeroFactura by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    var nombre by remember { mutableStateOf("") }
    var cif by remember { mutableStateOf("") }
    var baseImponible by remember { mutableStateOf("") }
    var iva by remember { mutableStateOf("21.0") } // IVA por defecto al 21%
    var irpf by remember { mutableStateOf("0.0") }
    var total by remember { mutableStateOf("0.0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Factura", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = AzulPrincipal)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Campo Número de Factura
                OutlinedTextField(
                    value = numeroFactura,
                    onValueChange = { numeroFactura = it },
                    label = { Text("Número de Factura") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                // Campo Fecha
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha de Emisión") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false // Fecha automática
                )
            }

            item {
                Text(
                    text = "Datos del Emisor:",
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            item {
                // Campo Nombre del Cliente / Proveedor
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Empresa:") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                // Campo CIF
                OutlinedTextField(
                    value = cif,
                    onValueChange = { cif = it },
                    label = { Text("CIF/NIF") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                // Campo Dirección
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Dirección:") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text(
                    text = "Datos del Cliente:",
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            item {
                // Campo Cliente
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Cliente:") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                // Campo CIF Cliente
                OutlinedTextField(
                    value = cif,
                    onValueChange = { cif = it },
                    label = { Text("CIF/NIF") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                // Campo Dirección
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Dirección:") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text(
                    text = "Importes de la factura:",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            item {
                // Campo Base Imponible
                OutlinedTextField(
                    value = baseImponible,
                    onValueChange = {
                        baseImponible = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("Base Imponible (€)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                // Campo IVA
                OutlinedTextField(
                    value = iva,
                    onValueChange = {
                        iva = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("IVA (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                // Campo IRPF
                OutlinedTextField(
                    value = irpf,
                    onValueChange = {
                        irpf = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("IRPF (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                // Campo Total
                OutlinedTextField(
                    value = total,
                    onValueChange = { total = it },
                    label = { Text("Total (€)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false // Se calcula automáticamente
                )
            }

            item {
                // Botón Guardar Factura
                Button(
                    onClick = {
                        val nuevaFactura = Factura(
                            numeroFactura = numeroFactura,
                            fecha = fecha,
                            nombre = nombre,
                            cif = cif,
                            baseImponible = baseImponible.toDoubleOrNull() ?: 0.0,
                            iva = iva.toDoubleOrNull() ?: 0.0,
                            irpf = irpf.toDoubleOrNull() ?: 0.0,
                            total = total.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.agregarFactura(nuevaFactura) // Guardar en Firebase
                        navController.popBackStack() // Volver a la lista de facturas
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar y Enviar", color = Color.White)
                }
            }
        }
    }
}

// 🔹 Función para calcular el total
fun calcularTotal(base: String, iva: String, irpf: String): String {
    val baseImponible = base.toDoubleOrNull() ?: 0.0
    val ivaPorcentaje = iva.toDoubleOrNull() ?: 0.0
    val irpfPorcentaje = irpf.toDoubleOrNull() ?: 0.0

    val ivaCalculado = baseImponible * (ivaPorcentaje / 100)
    val irpfCalculado = baseImponible * (irpfPorcentaje / 100)

    return String.format("%.2f", baseImponible + ivaCalculado - irpfCalculado)
}
