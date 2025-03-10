package com.example.proyectofacturas.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofacturas.R
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.ui.theme.Blanco
import com.example.proyectofacturas.ui.theme.colorDeFondo
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFacturas(navController: NavController, viewModel: FacturaViewModel) {
    val facturas by viewModel.facturas.observeAsState(emptyList())
    var filtroSeleccionado by remember { mutableStateOf("Todas") }

    Scaffold(
        topBar = { Header(navController) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(colorDeFondo)
        ) {
            FiltrosFacturas(filtroSeleccionado) { filtroSeleccionado = it }

            val facturasFiltradas = when (filtroSeleccionado) {
                "Compras" -> facturas.filter { it.tipo?.trim()?.lowercase() == "compra" }
                "Ventas" -> facturas.filter { it.tipo?.trim()?.lowercase() == "venta" }
                else -> facturas
            }

            if (facturasFiltradas.isEmpty()) {
                VistaFacturasVacias(navController)
            } else {
                ListaFacturas(facturasFiltradas, navController)
            }
        }
    }
}

@Composable
fun FiltrosFacturas(filtroSeleccionado: String, onFiltroSeleccionado: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Todas", "Compras", "Ventas").forEach { filtro ->
            ElevatedButton(
                onClick = { onFiltroSeleccionado(filtro) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = if (filtro == filtroSeleccionado) AzulPrincipal else Color.LightGray
                ),
                shape = MaterialTheme.shapes.large,
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp)
            ) {
                Text(filtro, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ListaFacturas(facturas: List<Factura>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        items(facturas) { factura ->
            ItemFactura(factura, navController)
        }
    }
}

@Composable
fun ItemFactura(factura: Factura, navController: NavController) {
    val formattedDate = try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(factura.fecha)
        date?.let { outputFormat.format(it) } ?: factura.fecha
    } catch (e: Exception) {
        factura.fecha
    }

    // Color según tipo de factura
    val colorTipo = when (factura.tipo.lowercase()) {
        "compra" -> Color(0xFF6366F1) // Azul-Violeta para Compras
        "venta" -> Color(0xFF10B981)  // Verde para Ventas
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { navController.navigate("detalle_factura/${factura.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono
            Icon(
                painter = painterResource(id = R.drawable.ic_invoice), // Asegúrate de tener este icono
                contentDescription = "Icono factura",
                tint = colorTipo,
                modifier = Modifier
                    .size(36.dp)
                    .background(colorTipo.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Información principal
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = factura.numeroFactura,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF111827)
                )
                Text(
                    text = formattedDate,
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280)
                )
            }

            // Total + Tipo de factura
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${"%.2f".format(factura.total)} €",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF111827)
                )
                Text(
                    text = factura.tipo.replaceFirstChar { it.uppercase() },
                    fontSize = 13.sp,
                    color = colorTipo,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
fun VistaFacturasVacias(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No hay facturas registradas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Añade una nueva factura para comenzar.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("crear_factura") },
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Crear Factura", color = Color.White)
        }
    }
}
