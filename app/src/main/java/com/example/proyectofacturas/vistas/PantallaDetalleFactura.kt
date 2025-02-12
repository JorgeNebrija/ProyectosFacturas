package com.example.proyectofacturas.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.ui.theme.colorDeFondo
import com.example.proyectofacturas.viewmodels.FacturaViewModel

@Composable
fun PantallaDetalleFactura(
    navController: NavHostController,
    idFactura: String,
    facturaViewModel: FacturaViewModel
) {
    val factura by facturaViewModel.obtenerFacturaPorId(idFactura).observeAsState()

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
            factura?.let {
                DetalleFactura(it)
            } ?: run {
                Text(
                    text = "Cargando factura...",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
fun DetalleFactura(factura: Factura) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Factura N.º: ${factura.numeroFactura}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Fecha de emisión: ${factura.fecha}")

        Spacer(modifier = Modifier.height(16.dp))

        SeccionDetalle("Datos del Emisor") {
            Text("Empresa: ${factura.nombre}")
            Text("NIF: ${factura.cif}")
            Text("Dirección: ${factura.direccion}")
        }

        SeccionDetalle("Datos del Receptor") {
            Text("Cliente: ${factura.cliente}")
            Text("NIF: ${factura.cifCliente}")
            Text("Dirección: ${factura.direccion}")
        }

        SeccionDetalle("Importes de la Factura") {
            Text("Base imponible: ${factura.baseImponible}")
            Text("IVA: ${factura.iva}")
            Text("Total: ${factura.total}")
        }
    }
}

@Composable
fun SeccionDetalle(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Text(
        text = titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        contenido()
    }
}


