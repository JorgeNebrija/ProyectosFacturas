package com.example.proyectofacturas.vistas

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.proyectofacturas.R
import java.util.concurrent.Executor

@Composable
fun PantallaAutenticacionBiometrica(navController: NavController) {
    val contexto = LocalContext.current
    var mensajeAutenticacion by remember { mutableStateOf("") }
    var autenticando by remember { mutableStateOf(false) }

    val sharedPref = contexto.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    val hasLoggedInBefore = sharedPref.getBoolean("hasLoggedInBefore", false)

    val executor: Executor = ContextCompat.getMainExecutor(contexto)

    val biometricPrompt = BiometricPrompt(
        contexto as FragmentActivity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                mensajeAutenticacion = "Autenticación Exitosa"
                navController.navigate("facturas")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                mensajeAutenticacion = "Autenticación Fallida"
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Bienvenido de nuevo!")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        .build()

    // Iniciar la autenticación automáticamente solo si ya ha hecho login antes
    LaunchedEffect(Unit) {
        if (hasLoggedInBefore && !autenticando) {
            autenticando = true
            biometricPrompt.authenticate(promptInfo)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Bienvenido de nuevo!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_face_id),
            contentDescription = "Face ID",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("O", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_fingerprint),
            contentDescription = "Huella Digital",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Iniciar sesión con usuario y contraseña",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(mensajeAutenticacion, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
