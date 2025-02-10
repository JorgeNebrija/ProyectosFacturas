package com.example.proyectofacturas.pantallas

import Factura
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FacturaListaVenta(
    facturas: List<Factura>,
    onEdit: (Factura) -> Unit,
    onDelete: (Factura) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(facturas) { factura ->
            FacturaItem(factura = factura, onEdit = onEdit, onDelete = onDelete)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun FacturaItem(factura: Factura, onEdit: (Factura) -> Unit, onDelete: (Factura) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface // En Material3, usamos `colorScheme`
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Factura N.º: ${factura.numeroFactura}", style = MaterialTheme.typography.titleLarge) // En Material3 se usa titleLarge
            Text("Fecha de emisión: ${factura.fechaEmision}", style = MaterialTheme.typography.bodyMedium) // body2 se cambia a bodyMedium
            Spacer(modifier = Modifier.height(8.dp))
            Text("Empresa: ${factura.empresaEmisora}", style = MaterialTheme.typography.bodyMedium)
            Text("Cliente: ${factura.clienteReceptor}", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onEdit(factura) }) {
                    Text("Editar")
                }
                Button(onClick = { onDelete(factura) }) {
                    Text("Eliminar")
                }
            }
        }
    }
}
