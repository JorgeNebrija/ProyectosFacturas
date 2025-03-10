package com.example.proyectofacturas.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { navController.navigate("detalle_factura/${factura.id}") },
        colors = CardDefaults.cardColors(
            containerColor = Blanco
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Factura N.º ${factura.numeroFactura}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Fecha: $formattedDate", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Total: ${factura.total} €", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AzulPrincipal)
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
