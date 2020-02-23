package com.example.hardwaresensors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_accelerometer_sensor.*

class AccelerometerSensorActivity : AppCompatActivity() {


    lateinit var sm : SensorManager
    lateinit var sensorEventListener : SensorEventListener
    lateinit var accelSensor : Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer_sensor)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sm.getSensorList(Sensor.TYPE_ALL)

        for(sensor in sensorList){
            Log.d("SENSOR", """
                ${sensor.name}
            ${sensor.vendor}
            """.trimIndent())
        }

        val proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorEventListener = object : SensorEventListener{
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                Log.d("SENSOR", "onAccuracyChanged")
            }

            override fun onSensorChanged(p0: SensorEvent?) {
                p0?.values?.let{
                    val bgColor = accelToColor(it[0], it[1], it[2])
                    root.setBackgroundColor(bgColor)
                    Log.d("SENSOR", """onSensorChanged
                    | ax = ${it[0]}
                    | ay = ${it[1]}
                    | az = ${it[2]}
                """.trimMargin())
                }
            }
        }
    }

    private fun accelToColor(ax: Float, ay: Float, az: Float): Int{
        val R = (((ax + 12)/24) * 255).toInt()
        val G = (((ay + 12)/24) * 255).toInt()
        val B = (((az + 12)/24) * 255).toInt()

        return Color.rgb(R, G, B)
    }

    override fun onResume() {
        super.onResume()

        sm.registerListener(
            sensorEventListener,
            accelSensor,
            1000 * 1
        )
    }

    override fun onPause() {
        sm.unregisterListener(sensorEventListener)
        super.onPause()
    }
}
