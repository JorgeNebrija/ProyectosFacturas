package com.example.proyectofacturas.vistas

import android.content.Context
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
import androidx.compose.ui.res.stringResource
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
    var showLogoutDialog by remember { mutableStateOf(false) } // Estado para la alerta de cerrar sesión
    var showPrivacyDialog by remember { mutableStateOf(false) } // Estado para la alerta de Políticas de Privacidad
    var showChangePasswordDialog by remember { mutableStateOf(false) }



    // Diálogo de información personal
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
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cerrar")
                }
            },
            containerColor = Blanco
        )
    }

    // Diálogo de confirmación de cierre de sesión
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Cerrar Sesión", color = Color.Black) },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?", color = Color.Black) },
            confirmButton = {
                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("pantallaLogin") {
                            popUpTo("pantallaPerfil") { inclusive = true }
                        }
                        showLogoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal)
                ) {
                    Text("Cerrar sesión")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancelar")
                }
            },
            containerColor = Blanco
        )
    }
    // Diálogo de Políticas de Privacidad
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Cerrar Sesión", color = Color.Black) },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?", color = Color.Black) },
            confirmButton = {
                Button(
                    onClick = {
                        cerrarSesion(navController, navController.context)
                        showLogoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal)
                ) {
                    Text("Cerrar sesión")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancelar")
                }
            },
            containerColor = Blanco
        )
    }

    // AQUÍ se coloca el if para mostrar la alerta cuando showChangePasswordDialog sea true
    if (showChangePasswordDialog) {
        CambiarContrasenaDialog(onDismiss = { showChangePasswordDialog = false })
    }


    // Recuperar datos del usuario desde Firestore
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
                text = userName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f)
        ) {
            PerfilOptionItem(icon = R.drawable.ic_informacion, text = "Información Personal") {
                showDialog = true
            }

            PerfilOptionItem(icon = R.drawable.ic_factura, text = "Facturas") {
                navController.navigate("facturas")
            }

            // Opción de Políticas de Privacidad con alerta
            PerfilOptionItem(icon = R.drawable.ic_politica, text = "Política de privacidad") {
                showPrivacyDialog = true // Muestra la alerta en lugar de navegar
            }

            // Opción de ver el video de introducción
            PerfilOptionItem(icon = R.drawable.ic_video, text = "Ver Introducción") {
                navController.navigate("pantallaVideo")
            }


            PerfilOptionItem(icon = R.drawable.ic_configuraciones, text = "Cambiar Contraseña") {
                showChangePasswordDialog = true
            }


            // Opción de cerrar sesión con diálogo de confirmación
            PerfilOptionItem(icon = R.drawable.ic_cerrar, text = "Cerrar sesión") {
                showLogoutDialog = true
            }
        }
    }
}

fun cerrarSesion(navController: NavHostController, context: Context) {
    val auth = FirebaseAuth.getInstance()

    // Obtener el cliente de Google Sign-In
    val googleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
    )

    // Cerrar sesión en Firebase
    auth.signOut()

    // Cerrar sesión en Google Sign-In
    googleSignInClient.signOut().addOnCompleteListener {
        googleSignInClient.revokeAccess().addOnCompleteListener {
            // Eliminar SharedPreferences para que no vuelva a iniciar con biometría
            val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean("hasLoggedInBefore", false)
                apply()
            }

            // Redirigir a pantalla de inicio de sesión
            navController.navigate("pantallaLogin") {
                popUpTo("pantallaPerfil") { inclusive = true }
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
@Composable
fun CambiarContrasenaDialog(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var mensajeExito by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Cambiar Contraseña", color = Color.Black) },
        text = {
            Column {
                Text(text = "Ingresa tu correo electrónico para recibir un enlace de recuperación.", color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                mensajeError?.let {
                    Text(text = it, color = Color.Red, fontSize = 14.sp)
                }

                mensajeExito?.let {
                    Text(text = it, color = Color.Green, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnSuccessListener {
                                mensajeExito = "Correo enviado. Revisa tu bandeja de entrada."
                                mensajeError = null
                            }
                            .addOnFailureListener { e ->
                                mensajeError = "Error: ${e.message}"
                                mensajeExito = null
                            }
                    } else {
                        mensajeError = "El correo no puede estar vacío."
                        mensajeExito = null
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal)
            ) {
                Text("Enviar Instrucciones")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancelar")
            }
        },
        containerColor = Blanco
    )
}


