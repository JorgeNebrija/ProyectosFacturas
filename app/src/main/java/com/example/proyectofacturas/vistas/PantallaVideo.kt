package com.example.proyectofacturas.vistas

import android.net.Uri
import android.widget.MediaController
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import android.widget.VideoView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

@Composable
fun PantallaVideo(navController: NavHostController) {
    val context = LocalContext.current //VAriable para conseguri el entorno actual de la aplicacion , para acceder a recursos de la aplicacion
    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/introduccion") // es un identificador para encontrar ese recurso

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E81F4)), // Azul
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            modifier = Modifier
                .padding(8.dp)
                .height(40.dp) // Ajuste de altura
        ) {
            Text(
                text = "Volver",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp) // Espaciado interno
            )
        }




        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoURI(videoUri)
                    setMediaController(MediaController(context).apply {
                        setAnchorView(this@apply)
                    })
                    start()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
