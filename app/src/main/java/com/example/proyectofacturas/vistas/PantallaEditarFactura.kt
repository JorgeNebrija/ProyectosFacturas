package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun PantallaEditarFactura(
    navController: NavController,
    idFactura: String,
    viewModel: FacturaViewModel
) {
    val factura by viewModel.obtenerFacturaPorId(idFactura).observeAsState()

    if (factura != null) {
        val facturaData = factura!!

        var baseImponible by remember { mutableStateOf(facturaData.baseImponible.toString()) }
        var iva by remember { mutableStateOf(facturaData.iva.toString()) }
        var irpf by remember { mutableStateOf(facturaData.irpf.toString()) }
        var total by remember { mutableStateOf(facturaData.total.toString()) }

        Scaffold(
            topBar = { Header(navController) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Editar Factura",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item { CampoSoloLectura("Número de Factura", facturaData.numeroFactura, Icons.Default.Description) }
                    item { CampoSoloLectura("Fecha", facturaData.fecha, Icons.Default.DateRange) }
                    item { CampoSoloLectura("Nombre Empresa", facturaData.nombre, Icons.Default.Business) }
                    item { CampoSoloLectura("Cliente", facturaData.cliente, Icons.Default.Person) }
                    item { CampoSoloLectura("Dirección Emisor", facturaData.direccion, Icons.Default.Home) }
                    item { CampoSoloLectura("Dirección Cliente", facturaData.direccionCliente, Icons.Default.Home) }
                    item { CampoSoloLectura("CIF Emisor", facturaData.cif, Icons.Default.Badge) }
                    item { CampoSoloLectura("CIF Cliente", facturaData.cifCliente, Icons.Default.Badge) }

                    item {
                        CampoEditable("Base Imponible", baseImponible, Icons.Default.AttachMoney) {
                            baseImponible = it
                        }
                    }
                    item {
                        CampoEditable("IVA", iva, Icons.Default.Percent) {
                            iva = it
                        }
                    }
                    item {
                        CampoEditable("IRPF", irpf, Icons.Default.Percent) {
                            irpf = it
                        }
                    }
                    item {
                        CampoEditable("Total", total, Icons.Default.Money) {
                            total = it
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                factura?.id?.let { facturaId ->
                                    val facturaActualizada = Factura(
                                        id = facturaId,
                                        numeroFactura = facturaData.numeroFactura,
                                        fecha = facturaData.fecha,
                                        nombre = facturaData.nombre,
                                        direccion = facturaData.direccion,
                                        cliente = facturaData.cliente,
                                        direccionCliente = facturaData.direccionCliente,
                                        cif = facturaData.cif,
                                        cifCliente = facturaData.cifCliente,
                                        baseImponible = baseImponible.toDoubleOrNull() ?: 0.0,
                                        iva = iva.toDoubleOrNull() ?: 0.0,
                                        irpf = irpf.toDoubleOrNull() ?: 0.0,
                                        total = total.toDoubleOrNull() ?: 0.0,
                                        tipo = facturaData.tipo
                                    )
                                    viewModel.actualizarFactura(facturaId, facturaActualizada)
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Guardar cambios", fontSize = 16.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando factura...", color = Color.Gray)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoSoloLectura(label: String, value: String, icon: ImageVector? = null) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = icon?.let { { Icon(it, contentDescription = null) } },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = Color.Gray,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.Gray)
    )
}


@Composable
fun CampoEditable(label: String, value: String, icon: ImageVector? = null, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = icon?.let { { Icon(it, contentDescription = null) } },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

