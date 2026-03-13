package com.example.gael_somer_anime.core.hardware

import androidx.fragment.app.FragmentActivity

interface BiometricManager {
    fun canAuthenticate(): Boolean
    fun authenticate(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}
