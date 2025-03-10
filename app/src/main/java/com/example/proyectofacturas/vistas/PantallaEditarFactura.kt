package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header

@OptIn(ExperimentalMaterial3Api::class)
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
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Editar Factura",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item { CampoSoloLectura("Número de Factura", facturaData.numeroFactura) }
                    item { CampoSoloLectura("Fecha", facturaData.fecha) }
                    item { CampoSoloLectura("Nombre Empresa", facturaData.nombre) }
                    item { CampoSoloLectura("Cliente", facturaData.cliente) }
                    item { CampoSoloLectura("Dirección Emisor", facturaData.direccion) }
                    item { CampoSoloLectura("Dirección Cliente", facturaData.direccionCliente) }
                    item { CampoSoloLectura("CIF Emisor", facturaData.cif) }
                    item { CampoSoloLectura("CIF Cliente", facturaData.cifCliente) }
                    item { CampoEditable("Base Imponible", baseImponible) { baseImponible = it } }
                    item { CampoEditable("IVA", iva) { iva = it } }
                    item { CampoEditable("IRPF", irpf) { irpf = it } }
                    item { CampoEditable("Total", total) { total = it } }
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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar cambios", color = Color.White)
                        }
                    }
                }
            }
        }
    } else {
        Text("Cargando factura...", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
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
            disabledTextColor = Color.Black,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.Black)
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

