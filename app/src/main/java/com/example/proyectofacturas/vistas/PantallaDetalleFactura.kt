package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("crear_factura") },
                containerColor = AzulPrincipal
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Factura", tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

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