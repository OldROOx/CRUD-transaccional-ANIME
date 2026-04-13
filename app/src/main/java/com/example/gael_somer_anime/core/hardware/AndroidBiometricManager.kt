package com.example.gael_somer_anime.core.hardware

import android.content.Context
import androidx.biometric.BiometricManager as AndroidBiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidBiometricManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : BiometricManager {
    private val applicationContext = context
    override fun canAuthenticate(): Boolean {
        val biometricManager = AndroidBiometricManager.from(applicationContext)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) == AndroidBiometricManager.BIOMETRIC_SUCCESS
    }

    override fun authenticate(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("Autenticación fallida")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Inicio de sesión biométrico")
            .setSubtitle("Usa tu huella digital para entrar")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
