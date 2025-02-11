package com.example.proyectofacturas.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofacturas.R
import com.example.proyectofacturas.ui.theme.AzulPrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(navController: NavController){
    TopAppBar(
        title = {

            Image(
                painter = painterResource(id = R.drawable.freelance_admin_logo),  // Reemplaza con el nombre de tu imagen
                contentDescription = "Logo de la empresa",
                modifier = Modifier.size(160.dp)
                    .clickable {
                        // Funcionalidad de navegación cuando se hace clic en la imagen
                        FuncionalidadNavegacion.irAPantallaFacturas(navController)
                    }

            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
    )
}