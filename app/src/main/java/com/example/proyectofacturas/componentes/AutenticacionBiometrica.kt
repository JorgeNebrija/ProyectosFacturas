package com.example.proyectofacturas.componentes

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class AutenticacionBiometrica(private val context: Context, private val onAuthSuccess: () -> Unit) {

    private val executor: Executor = ContextCompat.getMainExecutor(context)

    // Verifica si el dispositivo soporta autenticación biométrica
    fun esAutenticacionPermitida(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun obtenerPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Autentícate utilizando el sensor biométrico")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()
    }


    // Inicia la autenticación biométrica
    fun iniciarAutenticacion() {
        if (!esAutenticacionPermitida()) return

        val biometricPrompt = BiometricPrompt(
            context as androidx.fragment.app.FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Aquí puedes manejar el fallo si es necesario
                }
            }
        )

        biometricPrompt.authenticate(obtenerPromptInfo())
    }
}
