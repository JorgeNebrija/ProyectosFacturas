package com.example.proyectofacturas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofacturas.navegacion.NavigationWrapper
import com.example.proyectofacturas.ui.theme.TemaFacturaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaFacturaApp {
                NavigationWrapper()
            }
        }
    }
}
