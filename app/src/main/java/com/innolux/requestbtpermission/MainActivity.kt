package com.innolux.requestbtpermission

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100  // Can be any integer
    var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var btScanReceiver = BTScanReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnScanToggle: Button = findViewById(R.id.main_startScan_btn)
        btnScanToggle.setOnClickListener{
            // Check if the permission we want to use is been granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                // Grant the necessary permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE)
            } else {
                //  Necessary permissions are granted
                startDiscovering()
            }
        }
    }

    // When requesting permission is complete, analyze the result and do something corresponding
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && hasAllPermissionsGranted(grantResults))) {
                    // Permission granted! Start discovering
                    startDiscovering()
                } else {
                    // Permissions granted fail...
                    // Do something when permissions are not granted
                }
            }
        }
    }

    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    private fun startDiscovering() {
        if (bluetoothAdapter.isEnabled) {
            if (bluetoothAdapter.isDiscovering) {
                Toast.makeText(this, "BT Discovering.", Toast.LENGTH_SHORT).show()
            } else {
                bluetoothAdapter.startDiscovery()
                Toast.makeText(this, "Start BT discovering.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "BT is not enable...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        registerDiscoveryReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(btScanReceiver)
    }

    private fun registerDiscoveryReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        registerReceiver(btScanReceiver, intentFilter)
    }
}
