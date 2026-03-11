package com.example.gael_somer_anime.core.hardware

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.sqrt

class AndroidShakeDetectorImpl @Inject constructor(
    @ApplicationContext context: Context
) : ShakeDetector, SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var acceleration = 10f
    private var currentAcceleration = SensorManager.GRAVITY_EARTH
    private var lastAcceleration = SensorManager.GRAVITY_EARTH
    
    private var onShakeCallback: (() -> Unit)? = null

    override fun startListening(onShake: () -> Unit) {
        onShakeCallback = onShake
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
        onShakeCallback = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        
        lastAcceleration = currentAcceleration
        currentAcceleration = sqrt(x * x + y * y + z * z)
        val delta = currentAcceleration - lastAcceleration
        acceleration = acceleration * 0.9f + delta

        // Umbral de 12 para que sea sensible pero no se active solo
        if (acceleration > 12) {
            onShakeCallback?.invoke()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
