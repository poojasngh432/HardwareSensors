package com.example.hardwaresensors

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_gpssensor.*


class GPSSensorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpssensor)
        val finePerm = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarsePerm = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (finePerm == PackageManager.PERMISSION_DENIED || coarsePerm == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1122
            )
        } else {

            val locMan = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locListener = object: LocationListener {
                override fun onLocationChanged(location: Location?) {
                    latitude.setText("Latitude: " + location?.latitude.toString())
                    longitude.setText("Longitude: " + location?.longitude.toString())

                    Log.d("LOCATION", """
                        onLocationChanged
                        lat = ${location?.latitude}
                        long = ${location?.longitude}
                        accuracy = ${location?.accuracy}
                    """.trimIndent())
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }

                override fun onProviderEnabled(provider: String?) {
                }

                override fun onProviderDisabled(provider: String?) {
                }

            }
            locMan.allProviders.forEach {
                Log.d("LOCATION", "provider = ${it}")
            }

            locMan.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                (10 * 1000).toLong(),
                100F,
                locListener
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1122) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED ||
                grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    this,
                    "Cannot work without location permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
