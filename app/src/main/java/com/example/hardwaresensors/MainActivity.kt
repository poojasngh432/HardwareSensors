package com.example.hardwaresensors

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hardware_sensor_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AccelerometerSensorActivity::class.java)
            startActivity(intent)
        })
        location_btn.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(this, GPSSensorActivity::class.java)
            startActivity(intent2)
        })
    }

}
