package com.example.interactivemap.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.interactivemap.Constants.Keys
import com.example.interactivemap.Modules.JSONParser
import com.example.interactivemap.Modules.ObjectOpener
import com.example.interactivemap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CANCELED) {
                // Temporary do not needed
            }
        }

        val mapName = "vmk"

        binding.button.setOnClickListener {
            ObjectOpener.openMapMain(this, mapName, activityLauncher)
        }
    }
}