package com.example.proyectofacturas.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofacturas.modelos.Factura
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDeDatos(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    isNumberInput: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedBorderColor = Color.Transparent, // Borde transparente cuando está enfocado
            unfocusedBorderColor = Color.Transparent // Borde transparente cuando no está enfocado
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearFactura(navController: NavController, viewModel: FacturaViewModel) {

    var numeroFactura by remember { mutableStateOf(generarNumeroFactura()) }
    var fecha by remember {
        mutableStateOf(
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        )
    }

    val proyectos by viewModel.proyectos.observeAsState(emptyList())


    var nombre by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var direccionEmisor by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var cifEmisor by remember { mutableStateOf("") }
    var cifCliente by remember { mutableStateOf("") }
    var baseImponible by remember { mutableStateOf("") }
    var iva by remember { mutableStateOf("21.0") }
    var irpf by remember { mutableStateOf("0.0") }
    var total by remember { mutableStateOf("0.0") }

    var proyectoSeleccionadoNombre by remember { mutableStateOf("") }
    var proyectoSeleccionadoCodigo by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }



    var tipoFactura by remember { mutableStateOf("Compra") } // Estado de selección

    Scaffold(
        topBar = { Header(navController = navController) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Selección de Compra o Venta
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { tipoFactura = "Compra" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoFactura == "Compra") AzulPrincipal else Color.LightGray
                        )
                    ) {
                        Text("Compra", color = Color.White)
                    }

                    Button(
                        onClick = { tipoFactura = "Venta" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoFactura == "Venta") AzulPrincipal else Color.LightGray
                        )
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


            // Campos comunes
            item { InputDeDatos(value = numeroFactura, onValueChange = { numeroFactura = it }, label = "Número de Factura", enabled = false) }
            item { InputDeDatos(value = fecha, onValueChange = { fecha = it }, label = "Fecha de Emisión", enabled = false) }

            when (tipoFactura) {
                "Compra" -> {
                    item { Text("Datos del Proveedor:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(value = nombre, onValueChange = { nombre = it }, label = "Proveedor:", enabled = false) }
                    item { InputDeDatos(value = cifEmisor, onValueChange = { cifEmisor = it }, label = "CIF/NIF del Proveedor", enabled = false) }
                    item { InputDeDatos(value = direccionEmisor, onValueChange = { direccionEmisor = it }, label = "Dirección del Proveedor", enabled = false) }
                    item { Text("Datos del Cliente:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(value = cliente, onValueChange = { cliente = it }, label = "Cliente:", enabled = false) }
                    item { InputDeDatos(value = cifCliente, onValueChange = { cifCliente = it }, label = "CIF/NIF del Cliente", enabled = false) }
                    item { InputDeDatos(value = direccionCliente, onValueChange = { direccionCliente = it }, label = "Dirección del Cliente", enabled = false) }
                }
                "Venta" -> {
                    item { Text("Datos del Cliente:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(value = cliente, onValueChange = { cliente = it }, label = "Cliente:", enabled = false) }
                    item { InputDeDatos(value = cifCliente, onValueChange = { cifCliente = it }, label = "CIF/NIF del Cliente", enabled = false) }
                    item { InputDeDatos(value = direccionCliente, onValueChange = { direccionCliente = it }, label = "Dirección del Cliente", enabled = false) }
                    item { Text("Datos del Proveedor:", style = MaterialTheme.typography.titleSmall) }
                    item { InputDeDatos(value = nombre, onValueChange = { nombre = it }, label = "Proveedor:", enabled = false) }
                    item { InputDeDatos(value = cifEmisor, onValueChange = { cifEmisor = it }, label = "CIF/NIF del Proveedor", enabled = false) }
                    item { InputDeDatos(value = direccionEmisor, onValueChange = { direccionEmisor = it }, label = "Dirección del Proveedor", enabled = false) }

                }
            }

            // Importes
            item { Text("Importes de la factura:", style = MaterialTheme.typography.titleSmall) }
            item {
                InputDeDatos(
                    value = baseImponible,
                    onValueChange = {
                        baseImponible = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = "Base Imponible (€)",
                    keyboardType = KeyboardType.Number
                )
            }
            item {
                InputDeDatos(
                    value = iva,
                    onValueChange = {
                        iva = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = "IVA (%)",
                    keyboardType = KeyboardType.Number
                )
            }
            item {
                InputDeDatos(
                    value = irpf,
                    onValueChange = {
                        irpf = it
                        total = calcularTotal(baseImponible, iva, irpf)
                    },
                    label = "IRPF (%)",
                    keyboardType = KeyboardType.Number
                )
            }
            item {
                InputDeDatos(
                    value = total,
                    onValueChange = { total = it },
                    label = "Total (€)",
                    enabled = false
                )
            }


            // Botón de guardar
            item {
                Button(
                    onClick = {
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
                            total = total.toDoubleOrNull() ?: 0.0,
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

// Función para calcular el total
fun calcularTotal(base: String, iva: String, irpf: String): String {
    val baseImponible = base.toDoubleOrNull() ?: 0.0
    val ivaPorcentaje = iva.toDoubleOrNull() ?: 0.0
    val irpfPorcentaje = irpf.toDoubleOrNull() ?: 0.0

    val ivaCalculado = baseImponible * (ivaPorcentaje / 100)
    val irpfCalculado = baseImponible * (irpfPorcentaje / 100)

    return String.format("%.2f", baseImponible + ivaCalculado - irpfCalculado)
}

fun generarNumeroFactura(): String {
    val letras = (1..3)
        .map { ('A'..'Z').random() }
        .joinToString("")

    val numeros = (100..999).random()

    return "$letras/$numeros"
}

