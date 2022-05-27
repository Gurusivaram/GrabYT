package com.example.grabyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grabyt.databinding.ActivityMainBinding
import com.example.grabytdata.GrabYT

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GrabYT.grab("kJQP7kiw5Fk") {
            runOnUiThread {
                binding.tvJsonResult.text = it.toString()
                println("video Data --> $it")
            }
        }
    }
}