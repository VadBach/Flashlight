package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var flashlightOn: Boolean = false
    private lateinit var torchButton: Button
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        torchButton.setOnClickListener {
            if (!flashlightOn) {
                turnOnTorch()
            } else {
                turnOffTorch()
            }
        }
    }

    private fun init() {
        torchButton = findViewById(R.id.torch_button)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun turnOnTorch() {
        try {
            cameraManager.setTorchMode(cameraId, true)
            flashlightOn = true
            torchButton.text = getString(R.string.off)
            vibrate()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun turnOffTorch() {
        try {
            cameraManager.setTorchMode(cameraId, false)
            flashlightOn = false
            torchButton.text = getString(R.string.on)
            vibrate()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun vibrate() {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}