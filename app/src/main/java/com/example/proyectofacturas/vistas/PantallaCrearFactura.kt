package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.ui.theme.AzulClaro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDeDatos(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = icon?.let { { Icon(it, contentDescription = null, tint = if (enabled) AzulPrincipal else AzulClaro) } },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                disabledLabelColor = Color.Gray,
                disabledLeadingIconColor = AzulClaro
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearFactura(navController: NavController, viewModel: FacturaViewModel) {

    var numeroFactura by remember { mutableStateOf(generarNumeroFactura()) }
    var fecha by remember { mutableStateOf(SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())) }

    val proyectos by viewModel.proyectos.observeAsState(emptyList())

    var nombre by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var direccionEmisor by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var cifEmisor by remember { mutableStateOf("") }
    var cifCliente by remember { mutableStateOf("") }
    var baseImponible by remember { mutableStateOf("0.0") }
    var iva by remember { mutableStateOf("21.0") }
    var irpf by remember { mutableStateOf("0.0") }

    var proyectoSeleccionadoNombre by remember { mutableStateOf("") }
    var proyectoSeleccionadoCodigo by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var tipoFactura by remember { mutableStateOf("Compra") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Factura") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("facturas")
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { tipoFactura = "Compra" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoFactura == "Compra") AzulPrincipal else Color.LightGray
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Compra", color = Color.White)
                    }

                    Button(
                        onClick = { tipoFactura = "Venta" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoFactura == "Venta") AzulPrincipal else Color.LightGray
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Venta", color = Color.White)
                    }
                }
            }

            item {
                Text("Selecciona un Proyecto:", style = MaterialTheme.typography.titleSmall)
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = proyectoSeleccionadoNombre,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Proyecto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        proyectos.forEach { proyecto ->
                            DropdownMenuItem(
                                text = { Text(proyecto.nombre) },
                                onClick = {
                                    proyectoSeleccionadoNombre = proyecto.nombre
                                    proyectoSeleccionadoCodigo = proyecto.codigo
                                    cliente = proyecto.cliente
                                    cifCliente = proyecto.cifCliente
                                    direccionCliente = proyecto.direccionCliente

                                    nombre = proyecto.autor
                                    cifEmisor = proyecto.cifAutor
                                    direccionEmisor = proyecto.direccionAutor

                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            item { InputDeDatos(numeroFactura, {}, "Número de Factura", enabled = false) }
            item { InputDeDatos(fecha, {}, "Fecha de Emisión", enabled = false) }

            when (tipoFactura) {
                "Compra" -> {
                    item { Text("Datos del Proveedor:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(nombre, {}, "Proveedor:", icon = Icons.Default.Business, enabled = false) }
                    item { InputDeDatos(cifEmisor, {}, "CIF/NIF del Proveedor", icon = Icons.Default.Badge, enabled = false) }
                    item { InputDeDatos(direccionEmisor, {}, "Dirección del Proveedor", icon = Icons.Default.Home, enabled = false) }

                    item { Text("Datos del Cliente:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(cliente, {}, "Cliente:", icon = Icons.Default.Person, enabled = false) }
                    item { InputDeDatos(cifCliente, {}, "CIF/NIF del Cliente", icon = Icons.Default.Badge, enabled = false) }
                    item { InputDeDatos(direccionCliente, {}, "Dirección del Cliente", icon = Icons.Default.Home, enabled = false) }
                }

                "Venta" -> {
                    item { Text("Datos del Cliente:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(cliente, {}, "Cliente:", icon = Icons.Default.Person, enabled = false) }
                    item { InputDeDatos(cifCliente, {}, "CIF/NIF del Cliente", icon = Icons.Default.Badge, enabled = false) }
                    item { InputDeDatos(direccionCliente, {}, "Dirección del Cliente", icon = Icons.Default.Home, enabled = false) }

                    item { Text("Datos del Proveedor:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(nombre, {}, "Proveedor:", icon = Icons.Default.Business, enabled = false) }
                    item { InputDeDatos(cifEmisor, {}, "CIF/NIF del Proveedor", icon = Icons.Default.Badge, enabled = false) }
                    item { InputDeDatos(direccionEmisor, {}, "Dirección del Proveedor", icon = Icons.Default.Home, enabled = false) }
                }
            }

            item { Text("Importes de la factura:", style = MaterialTheme.typography.titleSmall) }
            item { InputDeDatos(baseImponible, { baseImponible = it }, "Base Imponible (€)", icon = Icons.Default.AttachMoney, keyboardType = KeyboardType.Number) }
            item { InputDeDatos(iva, { iva = it }, "IVA (%)", icon = Icons.Default.Percent, keyboardType = KeyboardType.Number) }
            item { InputDeDatos(irpf, { irpf = it }, "IRPF (%)", icon = Icons.Default.Percent, keyboardType = KeyboardType.Number) }
            item {
                InputDeDatos(
                    value = "%.2f".format(
                        calcularTotal(
                            baseImponible.toDoubleOrNull() ?: 0.0,
                            iva.toDoubleOrNull() ?: 0.0,
                            irpf.toDoubleOrNull() ?: 0.0
                        )
                    ),
                    onValueChange = {},
                    label = "Total (€)",
                    icon = Icons.Default.Money,
                    enabled = false
                )
            }

            item {
                Button(
                    onClick = {
                        val totalCalculado = calcularTotal(
                            baseImponible.toDoubleOrNull() ?: 0.0,
                            iva.toDoubleOrNull() ?: 0.0,
                            irpf.toDoubleOrNull() ?: 0.0
                        )
                        val nuevaFactura = Factura(
                            numeroFactura = numeroFactura,
                            fecha = fecha,
                            nombre = nombre,
                            direccion = direccionEmisor,
                            cliente = cliente,
                            direccionCliente = direccionCliente,
                            cif = cifEmisor,
                            cifCliente = cifCliente,
                            baseImponible = baseImponible.toDoubleOrNull() ?: 0.0,
                            iva = iva.toDoubleOrNull() ?: 0.0,
                            irpf = irpf.toDoubleOrNull() ?: 0.0,
                            total = totalCalculado,
                            tipo = tipoFactura,
                            codigoProyecto = proyectoSeleccionadoCodigo
                        )
                        viewModel.agregarFactura(nuevaFactura)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar y Enviar", color = Color.White)
                }
            }
        }
    }
}

fun calcularTotal(base: Double, iva: Double, irpf: Double): Double {
    val ivaCalculado = base * (iva / 100)
    val irpfCalculado = base * (irpf / 100)
    return base + ivaCalculado - irpfCalculado
}

fun generarNumeroFactura(): String {
    val letras = (1..3).map { ('A'..'Z').random() }.joinToString("")
    val numeros = (100..999).random()
    return "$letras/$numeros"
}
