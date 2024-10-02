package com.example.mlfacedetection1.uiscreens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mlfacedetection1.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectionActivity : AppCompatActivity() {
    private lateinit var imageCaptureLauncher: ActivityResultLauncher<Intent>
    private lateinit var capturedImageBitmap: Bitmap
    // Request code for camera permission
    private val REQUEST_CAMERA_PERMISSION = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_face_detection)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val bottonCamera = findViewById<Button>(R.id.btncamra)
        // Initialize the ActivityResultLauncher
        imageCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val extras = result.data?.extras
                if (extras?.get("data") != null) {
                    capturedImageBitmap = extras.get("data") as Bitmap
                    detectFace(capturedImageBitmap)
                    // Display the captured image in an ImageView
                    // imageView.setImageBitmap(capturedImageBitmap)
                }
            }
        }


// Set OnClickListener on the button
        bottonCamera.setOnClickListener {
            // Check for camera permission before launching the camera
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, launch the camera
                launchCamera()
            } else {
                // Request camera permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    // Launch the camera to capture an image
    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageCaptureLauncher.launch(cameraIntent)
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch the camera
                launchCamera()
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
            }
        }
    }


    private fun detectFace(bitmap: Bitmap) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap, 0)

        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully, out face detect successfully
                val faceDetails1 = findViewById<TextView>(R.id.person1)
                val faceDetails2 = findViewById<TextView>(R.id.person2)
                val faceDetails3 = findViewById<TextView>(R.id.person3)
                val faceDetails4 = findViewById<TextView>(R.id.person4)
                val faceDetails5 = findViewById<TextView>(R.id.person5)
                val faceDetails6 = findViewById<TextView>(R.id.person6)
                val faceDetails7 = findViewById<TextView>(R.id.person7)
                val faceDetails8 = findViewById<TextView>(R.id.person8)
                val faceDetails9 = findViewById<TextView>(R.id.person9)
                val faceDetails10 = findViewById<TextView>(R.id.person10)

                var ResultText = " "
                var i = 1
                for (face in faces) {
                    ResultText = "Face Number : $i" +
                            "\nSmile : ${face.smilingProbability?.times(100)}%" +
                            "\nLeft Eye Open : ${face.leftEyeOpenProbability?.times(100)}%" +
                            "\nRight Eye Open : ${face.rightEyeOpenProbability?.times(100)}%" +
                            "\n Head Euler AngleX:  ${face.headEulerAngleX}" +
                            "\n Head Euler AngleY ${face.headEulerAngleY}" +
                            "\n Head Euler AngleZ ${face.headEulerAngleZ}" +
                            "\n bounding Box ${face.boundingBox}"

                    when (i) {
                        1 -> faceDetails1.text = ResultText.toString()
                        2 -> faceDetails2.text = ResultText.toString()
                        3 -> faceDetails3.text = ResultText.toString()
                        4 -> faceDetails4.text = ResultText.toString()
                        5 -> faceDetails5.text = ResultText.toString()
                        6 -> faceDetails6.text = ResultText.toString()
                        7 -> faceDetails7.text = ResultText.toString()
                        8 -> faceDetails8.text = ResultText.toString()
                        9 -> faceDetails9.text = ResultText.toString()
                        else -> faceDetails10.text = ResultText.toString()
                    }


                    i++
                }


                if (faces.isEmpty()) {
                    Toast.makeText(this, "NO Face Detected", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, ResultText, Toast.LENGTH_LONG).show()
                }

            }
            .addOnFailureListener { e ->
                // Task failed with an exception ,face detection failed
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                // ...
            }

    }
}