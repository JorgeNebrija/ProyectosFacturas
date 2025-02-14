package com.example.proyectofacturas.vistas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.proyectofacturas.viewmodels.FacturaViewModel


@Composable
fun PantallaPerfil(navController: NavHostController, facturaViewModel: FacturaViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header con botón de retroceso
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = { /* Acción para volver atrás */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_volver),
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }
            Text(
                text = "My Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Foto de perfil y nombre
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(100.dp)
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
                text = "John Doe",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de perfil
        Column(modifier = Modifier.padding(16.dp)) {
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Profile")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Favorite")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Payment Method")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Privacy Policy")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Settings")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Help")
            PerfilOptionItem(icon = R.drawable.ic_perfil, text = "Logout")
        }
    }
}

@Composable
fun PerfilOptionItem(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
            painter = painterResource(id = R.drawable.ic_perfil),
            contentDescription = "Siguiente",
            tint = Color.Gray
        )
    }
}
