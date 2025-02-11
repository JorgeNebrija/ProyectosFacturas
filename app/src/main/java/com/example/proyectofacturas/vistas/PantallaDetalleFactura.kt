package com.example.proyectofacturas.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel

@Composable
fun PantallaDetalleFactura(
    navController: NavHostController,
    it: String,
    facturaViewModel: FacturaViewModel
) {

    val facturas by facturaViewModel.facturas.observeAsState(emptyList())

    Scaffold(
        topBar = { TopBarDetalles() },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {


            facturas.forEach { factura ->
                Text(
                    "Factura con Nº de factura:${factura.numeroFactura}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp),

                    )
                Text(
                    "Fecha de emisión: ${factura.fecha}",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Datos del emisor",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(text = "Empresa: ${factura.nombre}", color = Color.Black)
                    Text(text = "NIF: ${factura.cif}", color = Color.Black)
                    Text(text = "Dirección: ${factura.direccion}", color = Color.Black)

                }

                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Datos del receptor",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(text = "Cliente: ${factura.cliente}", color = Color.Black)
                    Text(text = "NIF: ${factura.cifCliente}", color = Color.Black)
                    Text(text = "Dirección: ${factura.direccion}", color = Color.Black)

                }

                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Importes de la factura",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(text = "Base imponible: ${factura.baseImponible}", color = Color.Black)
                    Text(text = "IVA: ${factura.iva}", color = Color.Black)
                    Text(text = "Total: ${factura.total}", color = Color.Black)

                }


            }


        }
    }
}

// Barra superior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDetalles() {
    TopAppBar(
        title = {
            Text(
                text = "Detalles de la Factura",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = AzulPrincipal)
    )
}