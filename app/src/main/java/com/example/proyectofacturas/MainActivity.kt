package com.example.proyectofacturas

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofacturas.navegacion.NavigationWrapper
import com.example.proyectofacturas.ui.theme.TemaFacturaApp

class MainActivity : AppCompatActivity() { //Añadimos AppCompatActivity para poder utilizar las opciomnes de biomeetrico ya que con ComponentActivity no se puede
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaFacturaApp {
                NavigationWrapper()
            }
        }
    }
}
