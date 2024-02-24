package com.example.interactivemap.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.interactivemap.Classes.DataType
import com.example.interactivemap.Constants.Debug
import com.example.interactivemap.Constants.Errors
import com.example.interactivemap.Constants.Keys
import com.example.interactivemap.Modules.JSONParser
import com.example.interactivemap.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapBinding
    var mapName: String? = null
    var dataTypesMap: MutableMap<String, DataType>? = null
    var activityLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapName = intent.getStringExtra(Keys.MAP_NAME_KEY)
        val objectJsonName = intent.getStringExtra(Keys.MAP_OBJECT_JSON_KEY)
        dataTypesMap = intent.getSerializableExtra(Keys.DATA_TYPES_MAP_KEY) as? MutableMap<String, DataType>

        if (mapName.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_MAP_NAME_ERROR)
            error(Errors.READ_MAP_NAME_ERROR)
        }
        if (objectJsonName.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_OBJECTS_JSON_NAME_ERROR)
            error(Errors.READ_OBJECTS_JSON_NAME_ERROR)
        }
        if (dataTypesMap.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_DATA_TYPES_MAP_ERROR)
            error(Errors.READ_DATA_TYPES_MAP_ERROR)
        }

        Log.d(Debug.MAP_OPENER_DEBUG,
            "Map name: $mapName\n" +
                "Object JSON name: $objectJsonName.json\n" +
                "Data types map: $dataTypesMap")

        activityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_CANCELED) {
                    // Temporary do not needed
                }
            }

        binding.mapView.activity = this

        val interactiveObjectsList = JSONParser.getJSONsInteractiveObjectsListList(
            this, mapName!!, objectJsonName, dataTypesMap!!
        )
        binding.mapView.objects = interactiveObjectsList
    }
}