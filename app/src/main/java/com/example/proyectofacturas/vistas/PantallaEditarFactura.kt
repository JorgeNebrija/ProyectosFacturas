package com.example.proyectofacturas.vistas

import androidx.compose.foundation.background
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
import com.example.proyectofacturas.ui.theme.AzulClaro
import com.example.proyectofacturas.ui.theme.colorDeFondo

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
            bottomBar = { BottomNavigationBar(navController) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Editar Factura",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item { CampoSoloLectura("Número de Factura", facturaData.numeroFactura, Icons.Default.Description) }
                item { CampoSoloLectura("Fecha", facturaData.fecha, Icons.Default.DateRange) }
                item { CampoSoloLectura("Nombre Empresa", facturaData.nombre, Icons.Default.Business) }
                item { CampoSoloLectura("Cliente", facturaData.cliente, Icons.Default.Person) }
                item { CampoSoloLectura("Dirección Emisor", facturaData.direccion, Icons.Default.Home) }
                item { CampoSoloLectura("Dirección Cliente", facturaData.direccionCliente, Icons.Default.Home) }
                item { CampoSoloLectura("CIF Emisor", facturaData.cif, Icons.Default.Badge) }
                item { CampoSoloLectura("CIF Cliente", facturaData.cifCliente, Icons.Default.Badge) }

                item { Text("Importes de la factura:", style = MaterialTheme.typography.titleSmall) }
                item { CampoEditable("Base Imponible (€)", baseImponible, Icons.Default.AttachMoney) { baseImponible = it } }
                item { CampoEditable("IVA (%)", iva, Icons.Default.Percent) { iva = it } }
                item { CampoEditable("IRPF (%)", irpf, Icons.Default.Percent) { irpf = it } }
                item { CampoEditable("Total (€)", total, Icons.Default.Money) { total = it } }

                item {
                    Button(
                        onClick = {
                            facturaData.id?.let { facturaId ->
                                val facturaActualizada = facturaData.copy(
                                    baseImponible = baseImponible.toDoubleOrNull() ?: 0.0,
                                    iva = iva.toDoubleOrNull() ?: 0.0,
                                    irpf = irpf.toDoubleOrNull() ?: 0.0,
                                    total = total.toDoubleOrNull() ?: 0.0
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
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando factura...")
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoSoloLectura(
    label: String,
    value: String,
    icon: ImageVector? = null
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = {
                Text(
                    text = label,
                    color = Color.Gray,
                    modifier = Modifier
                        .background(FondoLabel)
                )
            },
            leadingIcon = icon?.let { { Icon(it, contentDescription = null, tint = AzulClaro) } },
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.LightGray,
                disabledLabelColor = Color.Gray,
                disabledLeadingIconColor = AzulClaro,
                containerColor = Color.White
            ),
            textStyle = LocalTextStyle.current.copy(color = Color.Gray)
        )
    }
}

val FondoLabel = Color(0xFFF6F7FB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoEditable(
    label: String,
    value: String,
    icon: ImageVector? = null,
    onValueChange: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = Color.Gray,
                    modifier = Modifier
                        .background(FondoLabel)
                )
            },
            leadingIcon = icon?.let { { Icon(it, contentDescription = null, tint = AzulPrincipal) } },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}



