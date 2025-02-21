package com.example.proyectofacturas.componentes

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class AutenticacionBiometrica(
    private val context: Context,
    private val onAuthSuccess: () -> Unit,
    private val onAuthFailed: (() -> Unit)? = null,
    private val onAuthError: ((errorCode: Int, errString: CharSequence) -> Unit)? = null
) {

    private val executor: Executor = ContextCompat.getMainExecutor(context)

    // Verifica si el dispositivo soporta autenticación biométrica o credenciales del dispositivo (PIN, patrón, contraseña)
    fun esAutenticacionPermitida(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    // Configura el prompt para autenticación biométrica o credencial del dispositivo
    private fun obtenerPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación requerida")
            .setSubtitle("Usa tu huella, rostro, PIN o contraseña")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .setConfirmationRequired(true) // Asegura que se requiera confirmación
            .build()
    }

    // Inicia la autenticación biométrica o con PIN/contraseña si la biometría no está disponible
    fun iniciarAutenticacion() {
        if (!esAutenticacionPermitida()) {
            Log.e("AutenticacionBiometrica", "Autenticación no disponible en este dispositivo")
            onAuthError?.invoke(
                BiometricPrompt.ERROR_NO_BIOMETRICS,
                "Este dispositivo no admite autenticación biométrica ni credenciales seguras."
            )
            return
        }

        val biometricPrompt = BiometricPrompt(
            context as androidx.fragment.app.FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("AutenticacionBiometrica", "Autenticación exitosa")
                    onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.w("AutenticacionBiometrica", "Autenticación fallida")
                    onAuthFailed?.invoke()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("AutenticacionBiometrica", "Error de autenticación: $errString")
                    onAuthError?.invoke(errorCode, errString)
                }
            }
        )

        biometricPrompt.authenticate(obtenerPromptInfo())
    }
}
