package com.example.interactivemap

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.interactivemap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.activity = this

        val interactiveObjectsList = JSONParser.getJSONsInteractiveObjectsListList(this, "vmk")
        interactiveObjectsList.forEach {
            Log.d(GLOBAL_DEBUG, it.toString())
        }

        binding.mapView.objects = interactiveObjectsList
    }
}