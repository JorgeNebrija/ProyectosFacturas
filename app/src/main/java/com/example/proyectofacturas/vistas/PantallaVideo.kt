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
import androidx.compose.ui.graphics.Color

@Composable
fun PantallaVideo(navController: NavHostController) {
    val context = LocalContext.current
    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/introduccion")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver",
                    modifier = Modifier.padding(16.dp),
                color = Color.White)
        }



        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {
                    setVideoURI(videoUri)
                    setMediaController(MediaController(ctx).apply {
                        setAnchorView(this@apply)
                    })
                    start()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
