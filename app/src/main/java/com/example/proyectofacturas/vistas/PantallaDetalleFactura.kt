package com.example.proyectofacturas.vistas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.ui.theme.colorDeFondo
import com.example.proyectofacturas.ui.theme.colorTitulo
import com.example.proyectofacturas.viewmodels.FacturaViewModel

@Composable
fun PantallaDetalleFactura(
    navController: NavHostController,
    idFactura: String,
    facturaViewModel: FacturaViewModel
) {
    val factura by facturaViewModel.obtenerFacturaPorId(idFactura).observeAsState()
    val context = LocalContext.current

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
                DetalleFactura(it, navController, facturaViewModel, context)
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
fun DetalleFactura(
    factura: Factura,
    navController: NavHostController,
    facturaViewModel: FacturaViewModel,
    context: Context
) {
    val fondo = Color(0xFFF6F7FB)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = fondo
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cabecera con empresa y fecha
            item {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = factura.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = factura.fecha,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Datos del Emisor y Receptor
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetalleTarjeta("Datos del Emisor", listOf(
                        "Empresa: ${factura.nombre}",
                        "NIF: ${factura.cif}",
                        "Dirección: ${factura.direccion}"
                    ))
                    DetalleTarjeta("Datos del Receptor", listOf(
                        "Cliente: ${factura.cliente}",
                        "NIF: ${factura.cifCliente}",
                        "Dirección: ${factura.direccionCliente}"
                    ))
                }
            }

            // Desglose de importes
            item {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Importes de la Factura", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Base Imponible: ${factura.baseImponible}€")
                        Text("IVA: ${factura.iva}%")
                        Text("IRPF: ${factura.irpf}%")
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Total: ${factura.total}€",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            // Botones
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navController.navigate("editar_factura/${factura.id}")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Editar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            factura.id?.let { facturaViewModel.eliminarFactura(it) }
                            Toast.makeText(context, "Factura eliminada", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Borrar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleTarjeta(titulo: String, lineas: List<String>, modifier: Modifier = Modifier) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                color = AzulPrincipal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            lineas.forEach {
                Text(text = it, fontSize = 14.sp)
            }
        }
    }
}

