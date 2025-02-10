package com.example.proyectofacturas.ui.theme


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FacturaScreen() {
    // Estado mutable para los campos del formulario
    var fecha by remember { mutableStateOf("") }
    var numeroFactura by remember { mutableStateOf("") }
    var baseImponible by remember { mutableStateOf("") }
    var iva by remember { mutableStateOf("") }
    var irpf by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var clienteProveedor by remember { mutableStateOf("") }
    var cif by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestor de Facturas") })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Fecha de Expedición
                TextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha de expedición") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Número de factura
                TextField(
                    value = numeroFactura,
                    onValueChange = { numeroFactura = it },
                    label = { Text("Número de factura") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Base Imponible
                TextField(
                    value = baseImponible,
                    onValueChange = { baseImponible = it },
                    label = { Text("Base Imponible") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // IVA
                TextField(
                    value = iva,
                    onValueChange = { iva = it },
                    label = { Text("IVA") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // IRPF
                TextField(
                    value = irpf,
                    onValueChange = { irpf = it },
                    label = { Text("IRPF") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Total
                TextField(
                    value = total,
                    onValueChange = { total = it },
                    label = { Text("Total Importe") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // CIF y Cliente/Proveedor
                TextField(
                    value = clienteProveedor,
                    onValueChange = { clienteProveedor = it },
                    label = { Text("CIF y Nombre Cliente/Proveedor") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar la factura
                Button(
                    onClick = { /* Aquí deberías manejar el guardado */ },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Guardar Factura")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFacturaScreen() {
    TuAppTheme {
        FacturaScreen()
    }
}
