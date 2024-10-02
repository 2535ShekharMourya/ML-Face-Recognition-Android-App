package com.example.mlfacedetection1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mlfacedetection1.databinding.ActivityMainBinding
import com.example.mlfacedetection1.uiscreens.FaceDetectionActivity

class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgFaceDetection.setOnClickListener {
            startActivity(Intent(this,FaceDetectionActivity::class.java))
        }


    }


}