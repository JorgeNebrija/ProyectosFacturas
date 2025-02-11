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
    var cliente by remember { mutableStateOf("") }
    var direccionEmisor by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var cifEmisor by remember { mutableStateOf("") }
    var cifCliente by remember { mutableStateOf("") }
    var baseImponible by remember { mutableStateOf("") }
    var iva by remember { mutableStateOf("21.0") }
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
            item { OutlinedTextField(value = numeroFactura, onValueChange = { numeroFactura = it }, label = { Text("Número de Factura") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha de Emisión") }, modifier = Modifier.fillMaxWidth(), enabled = false) }
            item { Text(text = "Datos del Emisor:", style = MaterialTheme.typography.titleSmall) }
            item { OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Empresa:") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = cifEmisor, onValueChange = { cifEmisor = it }, label = { Text("CIF/NIF") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = direccionEmisor, onValueChange = { direccionEmisor = it }, label = { Text("Dirección:") }, modifier = Modifier.fillMaxWidth()) }
            item { Text(text = "Datos del Cliente:", style = MaterialTheme.typography.titleSmall) }
            item { OutlinedTextField(value = cliente, onValueChange = { cliente = it }, label = { Text("Cliente:") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = cifCliente, onValueChange = { cifCliente = it }, label = { Text("CIF/NIF") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = direccionCliente, onValueChange = { direccionCliente = it }, label = { Text("Dirección:") }, modifier = Modifier.fillMaxWidth()) }
            item { Text(text = "Importes de la factura:", style = MaterialTheme.typography.titleSmall) }

            item {
                OutlinedTextField(
                    value = baseImponible, onValueChange = {
                        baseImponible = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("Base Imponible (€)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item {
                OutlinedTextField(
                    value = iva, onValueChange = {
                        iva = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("IVA (%)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item {
                OutlinedTextField(
                    value = irpf, onValueChange = {
                        irpf = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = { Text("IRPF (%)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item { OutlinedTextField(value = total, onValueChange = { total = it }, label = { Text("Total (€)") }, modifier = Modifier.fillMaxWidth(), enabled = false) }
            item {
                Button(
                    onClick = {
                        val nuevaFactura = Factura(
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
                            total = total.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.agregarFactura(nuevaFactura)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal), modifier = Modifier.fillMaxWidth()
                ) { Text("Guardar y Enviar", color = Color.White) }
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
