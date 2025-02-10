import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PantallaMenu(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espacio reservado para mostrar facturas
            Text(
                text = "Facturas de Venta y Compra (aquí se mostrarán)",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botones para ir a las pantallas de creación de facturas
            Button(
                onClick = { navController.navigate("pantallaCreacionVenta") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Factura de Venta")
            }

            Button(
                onClick = { navController.navigate("pantallaCreacionCompra") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Factura de Compra")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones para las otras pantallas (Actualizar, Borrar, Mostrar)
            Button(
                onClick = { navController.navigate("pantallaActualizar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Factura")
            }

            Button(
                onClick = { navController.navigate("pantallaBorrar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar Factura")
            }

            Button(
                onClick = { navController.navigate("pantallaMostrar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar Facturas")
            }
        }
    }
}
