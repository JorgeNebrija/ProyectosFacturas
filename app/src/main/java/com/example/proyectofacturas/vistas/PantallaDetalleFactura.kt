package com.example.proyectofacturas.vistas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
    context: android.content.Context
) {
    Column(modifier = Modifier.padding(16.dp)) {
        SeccionDetalle("Detalles de la Factura N.º: ${factura.numeroFactura}") {
            Text("Fecha de emisión: ${factura.fecha}")
        }

        Text(
            "Datos de la Factura",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = Color.Black
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Datos del Emisor", fontWeight = FontWeight.Bold, color = colorTitulo)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Empresa: ${factura.nombre}")
                Text("NIF: ${factura.cif}")
                Text("Dirección: ${factura.direccion}")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Datos del Receptor", fontWeight = FontWeight.Bold, color = colorTitulo)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Cliente: ${factura.cliente}")
                Text("NIF: ${factura.cifCliente}")
                Text("Dirección: ${factura.direccionCliente}")
            }
        }

        SeccionDetalle("Importes de la Factura") {
            Text("Base imponible: ${factura.baseImponible}€")
            Text("IVA: ${factura.iva}%")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total: ${factura.total}€", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Editar y Eliminar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate("editar_factura/${factura.id}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal)
            ) {
                Text("Editar", color = Color.White)
            }

            Button(
                onClick = {
                    factura.id?.let { facturaViewModel.eliminarFactura(it) } //pone que el let ya que id coge un null y en firebase es obligatorio
                    Toast.makeText(context, "Factura eliminada", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Borrar", color = Color.White)
            }
        }
    }
}

@Composable
fun SeccionDetalle(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Text(
        text = titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        color = Color.Black
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
