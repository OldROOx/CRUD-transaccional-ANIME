package com.example.gael_somer_anime.core.hardware

interface ShakeDetector {
    fun startListening(onShake: () -> Unit)
    fun stopListening()
}
