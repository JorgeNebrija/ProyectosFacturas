package com.example.proyectofacturas.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofacturas.R
import com.example.proyectofacturas.componentes.BottomNavigationBar
import com.example.proyectofacturas.componentes.Header
import com.example.proyectofacturas.ui.theme.AzulPrincipal
import com.example.proyectofacturas.ui.theme.Blanco
import com.example.proyectofacturas.ui.theme.colorDeFondo
import com.example.proyectofacturas.viewmodels.FacturaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun PantallaPerfil(navController: NavHostController, facturaViewModel: FacturaViewModel) {
    var userName by remember { mutableStateOf("Cargando...") }
    var correo by remember { mutableStateOf("Cargando...") }
    var lastName by remember { mutableStateOf("Cargando...") }
    var telefono by remember { mutableStateOf("Cargando...") }
    var foto by remember { mutableStateOf("Cargando...") }
    var error by remember { mutableStateOf("Cargando...") }
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Información Personal", color = Color.Black) },
            text = {
                Column {
                    Text(text = "Nombre: $userName - $lastName", color = Color.Black)
                    Text(text = "Correo: $correo", color = Color.Black)
                    Text(text = "Teléfono: $telefono", color = Color.Black)
                }

            },
            confirmButton = {
                Button(onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(
                    containerColor = AzulPrincipal, // Color personalizado para el botón
                    contentColor = Color.White // Color del texto del botón
                )) {
                    Text("Cerrar")
                }
            },
                    containerColor = Blanco
        )
    } else {

    }


    // Recuperar el nombre del usuario desde Firestore
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: "Desconocido"
                    correo = document.getString("email") ?: "Desconocido"
                    lastName = document.getString("lastName") ?: "Desconocido"
                    telefono = document.getString("phone") ?: "Desconocido"
                    foto = document.getString("foto") ?: "Desconocido"
                }
                .addOnFailureListener {
                    error = "Error al cargar nombre"
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorDeFondo)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_volver),
                    contentDescription = "Volver",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Foto de perfil y nombre
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.image_facturas_vacias),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Gray, Color.LightGray)
                            ),
                            shape = CircleShape
                        )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Editar foto",
                    tint = Color(0xFF5E81F4),
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userName,  // Aquí se muestra el nombre del usuario
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de perfil
        Column(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f)  // Esto hará que esta columna ocupe el espacio restante
        ) {
            // Información Personal (Abre un diálogo)
            PerfilOptionItem(icon = R.drawable.ic_informacion, text = "Información Personal") {
                showDialog = true;
            }

            // Facturas (Navega a otra pantalla)
            PerfilOptionItem(icon = R.drawable.ic_factura, text = "Facturas") {
                navController.navigate("facturas")
            }

            // Política de privacidad (Navega a otra pantalla)
            PerfilOptionItem(icon = R.drawable.ic_politica, text = "Política de privacidad") {
                navController.navigate("politica")
            }

            // Configuraciones (Navega a otra pantalla)
            PerfilOptionItem(icon = R.drawable.ic_configuraciones, text = "Configuraciones") {
                navController.navigate("configuraciones")
            }

            // Cerrar sesión (Ejecuta la función de logout)
            PerfilOptionItem(icon = R.drawable.ic_cerrar, text = "Cerrar sesión") {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("pantallaLogin") {
                    popUpTo("pantallaPerfil") { inclusive = true }
                }
            }
        }
    }

}


@Composable
fun PerfilOptionItem(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() } // Llama a la función pasada al hacer clic
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = Color(0xFF5E81F4),
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE9F0FF), shape = CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_siguiente),
            contentDescription = "Siguiente",
            tint = Color(0xFF5E81F4),
            modifier = Modifier.size(16.dp)
        )
    }
}

