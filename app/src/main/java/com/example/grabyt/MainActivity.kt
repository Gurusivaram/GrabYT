package com.example.grabyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.grabyt.databinding.ActivityMainBinding
import com.example.grabytdata.GrabYT

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GrabYT.grab("video id goes here") {
            runOnUiThread {
                binding.tvJsonResult.text = it.toString()
                Log.d("Video Data",it.toString())
            }
        }
    }
}