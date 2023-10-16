package com.example.mlfacedetection1

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val bottonCamera=findViewById<Button>(R.id.btncamra)
       // val faceDetails=findViewById<TextView>(R.id.textView)

        bottonCamera.setOnClickListener {
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent.resolveActivity(packageManager)!=null){
            startActivityForResult(intent,123)
            }else{
                Toast.makeText(this,"oops something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123 && resultCode== RESULT_OK){
            val extra=data?.extras
            val bitmap=extra?.get("data") as? Bitmap
            if (bitmap !=null) {
                detectFace(bitmap)
            }
        }
    }

    private fun detectFace(bitmap: Bitmap) {
val options=FaceDetectorOptions.Builder()
    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
    .build()

        val detector=FaceDetection.getClient(options)
        val image=InputImage.fromBitmap(bitmap,0)

        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully, out face detect successfully
                val faceDetails1=findViewById<TextView>(R.id.person1)
                val faceDetails2=findViewById<TextView>(R.id.person2)
                val faceDetails3=findViewById<TextView>(R.id.person3)
                val faceDetails4=findViewById<TextView>(R.id.person4)
                val faceDetails5=findViewById<TextView>(R.id.person5)
                val faceDetails6=findViewById<TextView>(R.id.person6)
                val faceDetails7=findViewById<TextView>(R.id.person7)
                val faceDetails8=findViewById<TextView>(R.id.person8)
                val faceDetails9=findViewById<TextView>(R.id.person9)
                val faceDetails10=findViewById<TextView>(R.id.person10)

                    var ResultText=" "
                     var i=1
                for(face in faces){
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


                if (faces.isEmpty()){
                    Toast.makeText(this,"NO Face Detected",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,ResultText,Toast.LENGTH_LONG).show()
                }

            }
            .addOnFailureListener { e ->
                // Task failed with an exception ,face detection failed
                Toast.makeText(this,"Something Wrong",Toast.LENGTH_SHORT).show()
                // ...
            }

    }
}