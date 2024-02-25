package com.example.interactivemap.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.interactivemap.Classes.ClickModes
import com.example.interactivemap.Classes.Constants
import com.example.interactivemap.Classes.DataType
import com.example.interactivemap.Constants.Debug
import com.example.interactivemap.Constants.Keys
import com.example.interactivemap.Modules.JSONParser
import com.example.interactivemap.Modules.ObjectOpener
import com.example.interactivemap.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapBinding
    var mapName: String? = null
    var dataTypesMap: MutableMap<String, DataType>? = null
    var activityLauncher: ActivityResultLauncher<Intent>? = null
    var clickMode: ClickModes? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapName = intent.getStringExtra(Keys.MAP_NAME_KEY)
        val objectJsonName = intent.getStringExtra(Keys.MAP_OBJECT_JSON_KEY)
        dataTypesMap = intent.getSerializableExtra(Keys.DATA_TYPES_MAP_KEY) as? MutableMap<String, DataType>

        clickMode = Constants.DEFAULT_CLICK_MODE

        ObjectOpener.checkIfInputIsNull(mapName, objectJsonName, dataTypesMap)

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
        binding.changeModeBtn.setOnClickListener {
            nextMode()
            binding.changeModeBtn.text = binding.mapView.clickMode!!.modeName
        }

        binding.mapView.activity = this
        binding.mapView.clickMode = clickMode
        binding.changeModeBtn.text = clickMode!!.modeName

        val interactiveObjectsList = JSONParser.getJSONsInteractiveObjectsList(
            this, mapName!!, objectJsonName!!, dataTypesMap!!
        )
        binding.mapView.objects = interactiveObjectsList
    }
    private fun nextMode() {
        binding.mapView.clickMode = when (binding.mapView.clickMode) {
            ClickModes.OPEN_INSIDE_OBJECT -> ClickModes.OPEN_INFO
            ClickModes.OPEN_INFO -> ClickModes.OPEN_INSIDE_OBJECT
//            ClickModes.OPEN_INFO -> ClickModes.MOVE
//            ClickModes.MOVE -> ClickModes.OPEN_INSIDE_OBJECT
            else -> ClickModes.MOVE
        }
    }
}