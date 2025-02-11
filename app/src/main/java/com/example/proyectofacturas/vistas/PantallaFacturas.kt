package com.example.proyectofacturas.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.proyectofacturas.ui.theme.colorDeFondo
import com.example.proyectofacturas.viewmodels.FacturaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFacturas(navController: NavController, viewModel: FacturaViewModel) {
    val facturas by viewModel.facturas.observeAsState(emptyList())
    var filtroSeleccionado by remember { mutableStateOf("Todas") }

    Scaffold(
        topBar = { Header(navController = navController) },
        bottomBar = { MostrarBarraNavegacion(navController) } // Llamamos a la función aquí
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(colorDeFondo)) {
            // Barra de filtros
            FiltrosFacturas(filtroSeleccionado) { filtroSeleccionado = it }

            // Verificar si hay facturas o mostrar vista vacía
            val facturasFiltradas = when (filtroSeleccionado) {
                "Pagadas" -> facturas.filter { it.total > 0 }
                "Pendientes" -> facturas.filter { it.total == 0.0 }
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

// Barra de navegación
@Composable
fun MostrarBarraNavegacion(navController: NavController) {
    BottomNavigationBar(navController = navController)
}


// Barra de filtros
@Composable
fun FiltrosFacturas(filtroSeleccionado: String, onFiltroSeleccionado: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Todas", "Pagadas", "Pendientes").forEach { filtro ->
            Button(
                onClick = { onFiltroSeleccionado(filtro) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (filtro == filtroSeleccionado) AzulPrincipal else Color.LightGray
                )
            ) {
                Text(filtro, color = Color.White)
            }
        }
    }
}

//  Lista de facturas
@Composable
fun ListaFacturas(facturas: List<Factura>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(facturas) { factura ->
            ItemFactura(factura, navController)
        }
    }
}



@Composable
fun ItemFactura(factura: Factura, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("detalle_factura/${factura.id}") } // Pasamos el ID de la factura
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White  // Aquí definimos el color de fondo blanco
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Factura N.º ${factura.numeroFactura}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Fecha: ${factura.fecha}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Total: ${factura.total} €", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}



// Vista cuando no hay facturas
@Composable
fun VistaFacturasVacias(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_facturas_vacias),
            contentDescription = "No hay facturas",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal) //
        ) {
            Text("Crear Factura", color = Color.White)
        }
    }
}
